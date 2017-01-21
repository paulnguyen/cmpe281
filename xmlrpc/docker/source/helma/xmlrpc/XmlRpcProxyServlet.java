// Copyright 2000 Hannes Wallnfer

package helma.xmlrpc;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Vector;

/**
 * A  Servlet that acts as a XML-RPC Proxy . <p>
 * 
 * The URL of the server to connect to is taken from the init parameter <tt>url</tt>.
 */

public class XmlRpcProxyServlet extends HttpServlet {

    private XmlRpcServer xmlrpc;

    public void init (ServletConfig config) throws ServletException {
	if ("true".equalsIgnoreCase (config.getInitParameter ("debug")))
	    XmlRpc.setDebug (true);
	String url = config.getInitParameter ("url");
	xmlrpc = new XmlRpcServer ();
	try {
	    xmlrpc.addHandler ("$default", new XmlRpcClientLite (url));
	} catch (Exception x) {
	    throw new ServletException ("Invalid URL: "+url+" ("+x.toString ()+")");
	}
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException  {
	byte[] result = xmlrpc.execute (req.getInputStream ());
	res.setContentType("text/xml");
	res.setContentLength (result.length);
	OutputStream output = res.getOutputStream();
	output.write (result);
	output.flush ();
    }

}
