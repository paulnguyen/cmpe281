// Copyright 1999 Hannes Wallnfer, Raphael Spannocchi

package helma.xmlrpc;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.Vector;

/**
 * A prototype servlet to run XML-RPC. <p>
 * 
 * Note that some clients like the one in Frontier 5 and the first version of XmlRpcApplet 
 * had XML-RPC requests hard-coded to URI /RPC2. To work with these clients, you have 
 * to configure your servlet environment to respond to /RPC2. This has been fixed in the 
 * new version of the XmlRpcApplet.
 * 
 */

public class XmlRpcServlet extends HttpServlet implements XmlRpcHandler {

    public XmlRpcServer xmlrpc;

    public void init(ServletConfig config) throws ServletException {
	xmlrpc = new XmlRpcServer (); 
	xmlrpc.addHandler ("example", this);
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

    /** 
     * Callback method for XML-RPC server
     */
    public Object execute (String methodname, Vector params) {
	return params;
    }

}
