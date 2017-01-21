package helma.xmlrpc;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A minimal web server that exclusively handles XML-RPC requests.
 */
public class WebServer implements Runnable {

  XmlRpcServer xmlrpc;
  private ServerSocket serverSocket;
  private int port;
  private Thread listener;
  private boolean paranoid;
  private Vector accept, deny;
  private Stack threadpool;
  private ThreadGroup runners;


  static final byte[] ctype = "Content-Type: text/xml\r\n".getBytes();
  static final byte[] clength = "Content-Length: ".getBytes();
  static final byte[] newline = "\r\n".getBytes();
  static final byte[] doubleNewline = "\r\n\r\n".getBytes();
  static final byte[] conkeep = "Connection: Keep-Alive\r\n".getBytes();
  static final byte[] conclose = "Connection: close\r\n".getBytes();
  static final byte[] ok = " 200 OK\r\n".getBytes();
  static final byte[] server = "Server: Helma XML-RPC 1.0\r\n".getBytes();


  /**
   * This <em>can</em> be called from command line, but you'll have to edit and recompile
   * to change the server port or handler objects. By default, it sets up the following responders:
   * <ul><li> A java.lang.String object
   * <li> The java.lang.Math class (making its static methods callable via XML-RPC)
   * <li> An Echo handler that returns the argument array
   * </ul>
   */
  public static void main (String args[]) {
    System.err.println ("Usage: java helma.xmlrpc.WebServer [port]");
    int p = 8080; 
    if (args.length > 0) try {
      p = Integer.parseInt (args[0]);
    } catch (NumberFormatException nfx) {
      System.err.println ("Error parsing port number: "+args[0]);
    }
    // XmlRpc.setDebug (true);
    XmlRpc.setKeepAlive (true);
    try {
      WebServer webserver = new WebServer (p);
      // webserver.setParanoid (true);
      // webserver.acceptClient ("192.168.*.*");
      webserver.addHandler ("string", "Welcome to XML-RPC!");  
      webserver.addHandler ("math", Math.class);  
      webserver.addHandler ("auth", new AuthDemo());
      webserver.addHandler ("$default", new Echo());
      // XmlRpcClients can be used as Proxies in XmlRpcServers which is a cool feature for applets.
      webserver.addHandler ("mttf", new XmlRpcClient ("http://www.mailtothefuture.com:80/RPC2"));
      System.err.println ("started web server on port "+p);
    } catch (IOException x) {
      System.err.println ("Error creating web server: "+x);
    }

  }


  /**
   * Creates a Web server at the specified port number.
   */
  public WebServer (int port) throws IOException {
    this (port, null);
  }


  /**
   * Creates a Web server at the specified port number and IP address.
   */
  public WebServer (int port, InetAddress add) throws IOException {
    this.port = port;
    xmlrpc = new XmlRpcServer ();
    accept = new Vector ();
    deny = new Vector ();
    threadpool = new Stack ();
    runners = new ThreadGroup ("XML-RPC Runner");
    this.serverSocket = new ServerSocket (port, 50, add);
    listener = new Thread (this, "XML-RPC Weblistener");
    listener.start();
  }


  /**
   * Register a handler object with this name. Methods of this objects will be
   * callable over XML-RPC as "name.method".
   */
  public void addHandler (String name, Object target) {
    xmlrpc.addHandler (name, target);
  }

  /**
   * Remove a handler object that was previously registered with this server.
   */
  public void removeHandler (String name) {
    xmlrpc.removeHandler (name);
  }

  /**
   * Switch client filtering on/off. 
   * @see acceptClient(java.lang.String)
   * @see denyClient(java.lang.String)
   */
  public void setParanoid (boolean p) {
    paranoid = p;
  }
  

  /**
   * Add an IP address to the list of accepted clients. The parameter can contain '*' as wildcard
   * character, e.g. "192.168.*.*". You must call setParanoid(true) in order for this to have any
   * effect. 
   *
   * @see denyClient(java.lang.String)
   * @see setParanoid(boolean)
   */
  public void acceptClient (String address) throws IllegalArgumentException {
    try {
      AddressMatcher m = new AddressMatcher (address);
      accept.addElement (m);
    } catch (Exception x) {
      throw new IllegalArgumentException ("\""+address+"\" does not represent a valid IP address");
    }
  }

  /**
   * Add an IP address to the list of denied clients. The parameter can contain '*' as wildcard
   * character, e.g. "192.168.*.*". You must call setParanoid(true) in order for this to have any
   * effect. 
   *
   * @see acceptClient(java.lang.String)
   * @see setParanoid(boolean)
   */
  public void denyClient (String address) throws IllegalArgumentException {
    try {
      AddressMatcher m = new AddressMatcher (address);
      deny.addElement (m);
    } catch (Exception x) {
      throw new IllegalArgumentException ("\""+address+"\" does not represent a valid IP address");
    }
  }
  
  private boolean checkSocket (Socket s) {
    int l = deny.size ();
    byte address[] = s.getInetAddress ().getAddress ();
    for (int i=0; i<l; i++) {
      AddressMatcher match = (AddressMatcher) deny.elementAt (i);
      if (match.matches (address))
        return false;
    }
    l = accept.size ();
    for (int i=0; i<l; i++) {
      AddressMatcher match = (AddressMatcher) accept.elementAt (i);
      if (match.matches (address))
        return true;
    }
    return false;
  }

  /**
   * Listens for client requests until stopped.
   */  
  public void run() {
    try {
      while (listener != null) {
        try {
          Socket socket = serverSocket.accept();
          if (!paranoid || checkSocket (socket)) {
            Runner runner = getRunner ();
            runner.handle (socket);
            // new Connection (socket);
          } else
            socket.close ();
        } catch (Exception ex) {
            System.err.println("Exception in XML-RPC listener loop (" + ex + ").");
        } catch (Error err) {
            System.err.println("Error in XML-RPC listener loop (" + err + ").");
        }
      }
    } catch (Exception exception) {
      System.err.println("Error accepting XML-RPC connections (" + exception + ").");
    }
    finally {
      System.err.println("Closing XML-RPC server socket.");
      try {
        serverSocket.close();
        serverSocket = null;
      }
      catch (IOException ignore) {}
    }
  }


  /**
   * Stop listening on the server port.
   */
  public void shutdown () {
    if (listener != null) {
        Thread l = listener;
        listener = null;
        l.interrupt ();
    }
  }



  private Runner getRunner () {
    try {
      return (Runner) threadpool.pop ();
    } catch (EmptyStackException empty) {
      if (runners.activeCount () > 255)
        throw new RuntimeException ("System overload");
      return new Runner ();
    }
  }

  void releaseRunner (Runner runner) {
    threadpool.push (runner);
  }

  class Runner implements Runnable {

  Thread thread;
  Connection con;
  int count;

  public synchronized void handle (Socket socket) throws IOException {
    con = new Connection (socket);
    count = 0;
    if (thread == null || !thread.isAlive()) {
      thread = new Thread (runners, this);
      thread.start ();
    } else {
      notify ();
    }
  }

  public void run () {
    while (Thread.currentThread () == thread) {
      con.run ();
      count++;
      con = null;

      if (count > 200 || threadpool.size() > 20)
        return;

      synchronized (this) {
        releaseRunner (this);
        try {
            wait ();
        } catch (InterruptedException ir) {
            Thread.currentThread().interrupt();
        }
      }
    }
  }

}  // end class Runner


class Connection implements Runnable {

  private Socket  socket;
  private BufferedInputStream  input;
  private BufferedOutputStream output;
  // private Thread responder;
  private long lastRequest;
  private String user, password;

  public Connection (Socket socket) throws IOException {
    // set read timeout to 30 seconds
    socket.setSoTimeout (30000);

    this.socket = socket;
    input  = new BufferedInputStream (socket.getInputStream());
    output = new BufferedOutputStream (socket.getOutputStream());
    // responder = new Thread (this, "xmlrpc-worker");
    // responder.start();
  }


  public void run () {
    try {
      boolean keepalive = false;

      do {
        // reset user authentication
        user = password = null;
        String line = readLine ();
        // Netscape sends an extra \n\r after bodypart, swallow it
        if ("".equals (line))
          line = readLine();
        if (XmlRpc.debug)
            System.err.println (line);
        // get time of last request
        lastRequest = System.currentTimeMillis ();
        int contentLength = -1;
        
        // tokenize first line of HTTP request
        StringTokenizer tokens = new StringTokenizer(line);
        String method = tokens.nextToken();
        String uri = tokens.nextToken ();
        String httpversion = tokens.nextToken ();
        keepalive = XmlRpc.getKeepAlive() &&  "HTTP/1.1".equals (httpversion);
        do {	
          line = readLine();
          if (line != null) {
            if (XmlRpc.debug) 
              System.err.println (line);
            String lineLower = line.toLowerCase ();
            if (lineLower.startsWith ("content-length:"))
              contentLength = Integer.parseInt (line.substring (15).trim ());
            if (lineLower.startsWith ("connection:"))
              keepalive = XmlRpc.getKeepAlive() && lineLower.indexOf ("keep-alive") > -1;
            if (lineLower.startsWith ("authorization: basic "))
              parseAuth (line);
          }
        } while (line != null && ! line.equals(""));

        if ("POST".equalsIgnoreCase (method)) {
          ServerInputStream sin = new ServerInputStream (input, contentLength);
          byte result[] = xmlrpc.execute (sin, user, password);
          output.write (httpversion.getBytes());
          output.write (ok);
          output.write (server);
          if (keepalive)
            output.write (conkeep);
          else
            output.write (conclose);
          output.write (ctype);
          output.write (clength);
          output.write (Integer.toString (result.length).getBytes());
          output.write (doubleNewline);
           output.write (result);
          output.flush ();
        } else {
          output.write (httpversion.getBytes());
          output.write (" 400 Bad Request\r\n".getBytes());
          output.write ("Server: helma.XML-RPC\r\n\r\n".getBytes());
          output.write (("Method "+method+" not implemented (try POST)").getBytes());
          output.flush ();
          keepalive = false;
        }
      } while (keepalive);
    } catch (Exception exception) {
        if (XmlRpc.debug) {
          System.err.println (exception);
          exception.printStackTrace ();
        }
    } finally {
      try {
        socket.close();
      } catch (IOException ignore) {}
    }
  }

  byte[] buffer;
  private String readLine () throws IOException {
      if (buffer == null) {
        buffer = new byte[512];
      }
      int next;
      int count = 0;
      for (;;) {
        next = input.read();	
        if (next < 0 || next == '\n')
          break;
        if (next != '\r') {
          buffer[count++] = (byte) next;
        }
        if (count >= 512)
          throw new IOException ("HTTP Header too long");
      }
      return new String (buffer, 0, count);
  }

  private void parseAuth (String line) {
    try {
      byte[] c = Base64.decode (line.substring (21).getBytes());
      String str = new String (c);
      int col = str.indexOf (":");
      user = str.substring (0, col);
      password = str.substring (col+1);
    } catch (Throwable ignore) {}
  }

}


class AddressMatcher {

    int pattern[];
    
    public AddressMatcher (String address) throws Exception {
      pattern = new int[4];
      StringTokenizer st = new StringTokenizer (address, ".");
      if (st.countTokens () != 4)
        throw new Exception ("\""+address+"\" does not represent a valid IP address");
      for (int i=0; i<4; i++) {
        String next = st.nextToken ();
        if ("*".equals (next))
          pattern[i] = 256; 
        else
          pattern[i] = (byte) Integer.parseInt (next);
      }
    }
    
    public boolean matches (byte address[]) {
      for (int i=0; i<4; i++) {
        if (pattern[i] > 255) // wildcard
          continue;
        if (pattern[i] != address[i])
          return false;
      }
      return true;
    }
}

}

// An echo handler for debugging purposes
class Echo implements XmlRpcHandler {
    public Object execute (String method, Vector v) throws Exception {
      return (v);
    }
}

// An simple class that implements authentication
class AuthDemo implements AuthenticatedXmlRpcHandler {
    public Object execute (String method, Vector v, String user, String password) throws Exception {
      // our simplistic authentication guidelines never fail ;)
      if (user == null || user.startsWith ("bad"))
        throw new XmlRpcException (5, "Sorry, you're not allowed in here!");
      return ("Hello "+user);
    }
}

