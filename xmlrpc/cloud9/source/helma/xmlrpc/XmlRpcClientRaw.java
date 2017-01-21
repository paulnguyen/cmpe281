/**
 * Copyright 1999 Hannes Wallnoefer
 * Implements a XML-RPC client. See http://www.xmlrpc.com/
 */

package helma.xmlrpc;

import java.net.*;
import java.io.*;
import java.util.*;
import org.xml.sax.*;

/**
 * A multithreaded, reusable XML-RPC client object. This version uses a homegrown
 * HTTP client which can be quite a bit faster than java.net.URLConnection, especially
 * when used with XmlRpc.setKeepAlive(true).
 */
public class XmlRpcClientRaw extends XmlRpcClient {

    /**
     * Construct a XML-RPC client with this URL.
     */
    public XmlRpcClientRaw (URL url) {
	super (url);
    }

    /**
     * Construct a XML-RPC client for the URL represented by this String.
     */
    public XmlRpcClientRaw (String url) throws MalformedURLException {
	super (url);
    }

    /**
     * Construct a XML-RPC client for the specified hostname and port.
     */
    public XmlRpcClientRaw (String hostname, int port) throws MalformedURLException {
	super (hostname, port);
    }


    /**
     * Generate an XML-RPC request and send it to the server. Parse the result and
     * return the corresponding Java object.
     *
     * @exception XmlRpcException: If the remote host returned a fault message.
     * @exception IOException: If the call could not be made because of lower level problems.
     */
    public String execute (String xmlreq) throws XmlRpcException, IOException {
    	Worker worker = getWorker ();
	try {
    	    String retval =  worker.execute (xmlreq);
	    return retval;
	} finally {
	    if (workers < 50 && !worker.fault)
	        pool.push (worker);
	    else
	        workers -= 1;
	}
    }

    Stack pool = new Stack ();
    int workers = 0;

    private final Worker getWorker () throws IOException {
	try {
	    return (Worker) pool.pop ();
	} catch (EmptyStackException x) {
	    if (workers < 100) {
	        workers += 1;
	        return new Worker ();
	    }
	    throw new IOException ("XML-RPC System overload");
	}
    }


    class Worker extends XmlRpc {

    boolean fault;
    Object result = null;
    HttpClient client = null;
    StringBuffer strbuf;

    public Worker () {
    	super ();
    }


    public String execute (String xmlreq) throws XmlRpcException, IOException {
	long now = System.currentTimeMillis ();
	fault = false;
        String result = "" ;

    	try {

	    byte[] request = xmlreq.getBytes() ;

	    // and send it to the server
	    if (client == null)
	        client = new HttpClient (url);

	    	client.write (request);

    	    InputStream in = client.getInputStream ();


            // display raw xml
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            String sData = null ;
            try {
                boolean go = true ;
       		while ( go ) {
                  sData = bin.readLine() ;
                  //System.out.println( sData ) ;

                if ( sData == null )   {
                    go = false ;
        		}
                else {
                  result = result + sData ;
                }
            }
            }
            catch ( IOException iox ) {
                System.err.println ( iox.getMessage() ) ;
            }

	    // client keepalive is always false if XmlRpc.keepalive is false
	    if (!client.keepalive)
	        client.closeConnection ();

	    if (debug)
	        System.err.println ("result = "+result);

	    // check for errors from the XML parser
	    //if (errorLevel == FATAL)
	    //    throw new Exception (errorMsg);


	} catch (IOException iox) {
	    // this is a lower level problem,  client could not talk to server for some reason.
	    throw iox;

	} catch (Exception x) {
	    // same as above, but exception has to be converted to IOException.
	    if (XmlRpc.debug)
	        x.printStackTrace ();

	    String msg = x.getMessage ();
	    if (msg == null || msg.length () == 0)
	        msg = x.toString ();
	    throw new IOException (msg);
	}

        System.err.println ("Spent "+(System.currentTimeMillis () - now)+" millis in request");

	return result;
    }


    /**
     * Called when the return value has been parsed.
     */
    void objectParsed (Object what) {
	result = what;
    }


    /**
     * Generate an XML-RPC request from a method name and a parameter vector.
     */
    void writeRequest (XmlWriter writer, String method, Vector params) throws IOException {
	writer.startElement ("methodCall");

	writer.startElement ("methodName");
	writer.write (method);
	writer.endElement ("methodName");

	writer.startElement ("params");
	int l = params.size ();
	for (int i=0; i<l; i++) {
	    writer.startElement ("param");
	    writeObject (params.elementAt (i), writer);
	    writer.endElement ("param");
	}
	writer.endElement ("params");
	writer.endElement ("methodCall");
    }

    /**
     * Overrides method in XmlRpc to handle fault repsonses.
     */
    public void startElement (String name, AttributeList atts) throws SAXException {
	if ("fault".equals (name))
	    fault = true;
	else
	    super.startElement (name, atts);
    }

    } // end of class Worker


    // A replacement for java.net.URLConnection, which seems very slow on MS Java.
    class HttpClient {

    	String hostname;
    	String host;
    	int port;
    	String uri;
    	Socket socket = null;
    	BufferedOutputStream output;
	BufferedInputStream input;
	boolean keepalive;
	boolean fresh;


	public HttpClient (URL url) throws IOException {
	    hostname = url.getHost ();
	    port = url.getPort ();
	    if (port < 1) port = 80;
	    uri = url.getFile ();
	    if (uri == null || "".equals (uri))
	        uri = "/";
	    host = port == 80 ? hostname : hostname+":"+port;
	    initConnection ();
	}

	protected void initConnection () throws IOException {
	    fresh = true;
	    socket = new Socket (hostname, port);
	    output = new BufferedOutputStream (socket.getOutputStream());
	    input = new BufferedInputStream (socket.getInputStream ());
	}

	protected void closeConnection () {
	    try {
	        socket.close ();
	    } catch (Exception ignore) {}
	}

	public void write (byte[] request) throws IOException {
	    try {
	        output.write (("POST "+uri+" HTTP/1.0\r\n").getBytes());
	        output.write (("User-Agent: "+XmlRpc.version+"\r\n").getBytes());
	        output.write (("Host: "+host+"\r\n").getBytes());
	        if (XmlRpc.getKeepAlive())
	            output.write ("Connection: Keep-Alive\r\n".getBytes());
	        output.write ("Content-Type: text/xml\r\n".getBytes());
	        if (auth != null)
	            output.write (("Authorization: Basic "+auth+"\r\n").getBytes());
	        output.write (("Content-Length: "+request.length).getBytes());
	        output.write ("\r\n\r\n".getBytes());
	        output.write (request);

            //System.out.write( request ) ;
            //System.out.println() ;

	        output.flush ();
	        fresh = false;
	    } catch (IOException iox) {
	        // if the connection is not "fresh" (unused), the exception may have occurred
	        // because the server timed the connection out. Give it another try.
	        if (!fresh) {
	            initConnection ();
	            write (request);
	        } else {
	            throw (iox);
	        }
	    }
	}


	public InputStream getInputStream () throws IOException {
	    String line = readLine ();
	    if (XmlRpc.debug)
	        System.err.println (line);
	    int contentLength = -1;
	    try {
	        StringTokenizer tokens = new StringTokenizer (line);
	        String httpversion = tokens.nextToken ();
	        String statusCode = tokens.nextToken();
	        String statusMsg = tokens.nextToken ("\n\r");
	        keepalive = XmlRpc.getKeepAlive() && "HTTP/1.1".equals (httpversion);
	        if (!"200".equals (statusCode))
	            throw new IOException ("Unexpected Response from Server: "+statusMsg);
	    } catch (IOException iox) {
	        throw iox;
	    } catch (Exception x) {
	        x.printStackTrace ();
	        throw new IOException ("Server returned invalid Response.");
	    }
	    do {
	        line = readLine ();
	        if (line != null) {
	            if (XmlRpc.debug)
	                System.err.println (line);
	            line = line.toLowerCase ();
	            if (line.startsWith ("content-length:"))
	                contentLength = Integer.parseInt (line.substring (15).trim ());
	            if (line.startsWith ("connection:"))
	                keepalive = XmlRpc.getKeepAlive() && line.indexOf ("keep-alive") > -1;
	        }
	    } while (line != null && ! line.equals(""));

	    return new ServerInputStream (input, contentLength);
	}


	byte[] buffer;
	private String readLine () throws IOException {
	    if (buffer == null)
	        buffer = new byte[512];
	    int next;
	    int count = 0;
	    while (true) {
	        next = input.read();
	        if (next < 0 || next == '\n')
	           break;
	        if (next != '\r')
	            buffer[count++] = (byte) next;
	        if (count >= 512)
	            throw new IOException ("HTTP Header too long");
	    }
	    return new String (buffer, 0, count);
	}


	protected void finalize () throws Throwable {
	    closeConnection ();
	}

  }

    /**
     * Just for testing.
     */
    public static void main (String args[]) throws Exception {
    	// XmlRpc.setDebug (true);
	try {
	    String url = args[0];
	    String method = args[1];
	    Vector v = new Vector ();
	    for (int i=2; i<args.length; i++) try {
	        v.addElement (new Integer (Integer.parseInt (args[i])));
	    } catch (NumberFormatException nfx) {
	        v.addElement (args[i]);
	    }
	    XmlRpcClient client = new XmlRpcClientLite (url);
	    try {
	        System.err.println (client.execute (method, v));
	    } catch (Exception ex) {
	        System.err.println ("Error: "+ex.getMessage());
	    }
	} catch (Exception x) {
	    System.err.println (x);
	    System.err.println ("Usage: java helma.xmlrpc.XmlRpcClient <url> <method> <arg> ....");
	    System.err.println ("Arguments are sent as integers or strings.");
	}
    }


}


