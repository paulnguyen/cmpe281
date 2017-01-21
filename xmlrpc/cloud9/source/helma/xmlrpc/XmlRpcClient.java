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
 * A multithreaded, reusable XML-RPC client object. Use this if you need a full-grown
 * HTTP client (e.g. for Proxy and Cookies support). If you don't need that, <code>XmlRpcClientLite</code>
 * may work better for you.
 */
public class XmlRpcClient implements XmlRpcHandler {
     
    URL url;
    String auth;
   
    /** 
     * Construct a XML-RPC client with this URL.
     */
    public XmlRpcClient (URL url) {
	this.url = url;
    }

    /** 
     * Construct a XML-RPC client for the URL represented by this String.
     */
    public XmlRpcClient (String url) throws MalformedURLException {
	this.url = new URL (url);
    }
   
    /** 
     * Construct a XML-RPC client for the specified hostname and port.
     */
    public XmlRpcClient (String hostname, int port) throws MalformedURLException {
	this.url = new URL ("http://"+hostname+":"+port+"/RPC2");
    }
   

    /**
     * Sets Authentication for this client. This will be sent as Basic Authentication header
     * to the server as described in <a href="http://www.ietf.org/rfc/rfc2617.txt">http://www.ietf.org/rfc/rfc2617.txt</a>.
     */
    public void setBasicAuthentication (String user, String password) {
	if (user == null || password == null)
	    auth = null;
	else {
	    char[] basicAuth = Base64.encode ((user+":"+password).getBytes());
	    auth = new String (basicAuth).trim();
	}
    }

    /** 
     * Generate an XML-RPC request and send it to the server. Parse the result and
     * return the corresponding Java object.
     * 
     * @exception XmlRpcException: If the remote host returned a fault message.
     * @exception IOException: If the call could not be made because of lower level problems.
     */
    public Object execute (String method, Vector params) throws XmlRpcException, IOException {
    	Worker worker = getWorker ();
	try {
    	    Object retval =  worker.execute (method, params);
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
    StringBuffer strbuf;

    public Worker () throws IOException {
    	super ();
    }


    public Object execute (String method, Vector params) throws XmlRpcException, IOException {
	fault = false;
	long now = System.currentTimeMillis ();
    	try {
	    ByteArrayOutputStream bout = new ByteArrayOutputStream ();

	    if (strbuf == null)
	        strbuf = new StringBuffer ();
	    else
	        strbuf.setLength (0);
	    XmlWriter writer = new XmlWriter (strbuf);
	    writeRequest (writer, method, params);
	    byte[] request = writer.getBytes();

	    URLConnection con = url.openConnection ();
	    con.setDoInput (true);
	    con.setDoOutput (true);
	    con.setUseCaches (false);
	    con.setAllowUserInteraction(false);
	    con.setRequestProperty ("Content-Length", Integer.toString (request.length));
	    con.setRequestProperty ("Content-Type", "text/xml");
	    if (auth != null)
	        con.setRequestProperty ("Authorization", "Basic "+auth);
	    // con.connect ();
	    OutputStream out = con.getOutputStream ();
	    out.write (request);
	    out.flush ();
	    InputStream in = con.getInputStream ();
	    parse (in);
	} catch (Exception x) {
	    x.printStackTrace ();
	    throw new IOException (x.getMessage ());
	}
	if (fault) { // generate an XmlRpcException
	    XmlRpcException exception = null;
	    try {
	        Hashtable f = (Hashtable) result;
	        String faultString = (String) f.get ("faultString");
	        int faultCode = Integer.parseInt (f.get ("faultCode").toString ());
	        exception = new XmlRpcException (faultCode, faultString.trim ());
	    } catch (Exception x) {
	        throw new XmlRpcException (0, "Invalid fault response");
	    }
	    throw exception;
	}
	if (debug)
	    System.err.println ("Spent "+(System.currentTimeMillis () - now)+" in request");
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

    } // end of inner class Worker
    
    
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
	    XmlRpcClient client = new XmlRpcClient (url);
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


