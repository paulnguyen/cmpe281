package edu.sjsu.cmpe281 ;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.net.MalformedURLException;
import java.util.Vector;

import helma.xmlrpc.* ;

public class XmlRpcHelloServer extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

	XmlRpcServer xmlrpc = new XmlRpcServer () ;
	xmlrpc.addHandler( "HELLO", new XmlRpcHelloHandler() ) ;

	byte[] result = xmlrpc.execute( req.getInputStream() ) ;
	res.setContentType( "text/xml" ) ;
	res.setContentLength( result.length ) ;
	OutputStream out = res.getOutputStream() ;
	out.write( result ) ;
	out.flush() ;

 }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    doGet(req, res);
  }
}

