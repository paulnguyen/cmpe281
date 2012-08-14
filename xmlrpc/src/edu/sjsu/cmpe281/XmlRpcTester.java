package edu.sjsu.cmpe281 ;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.net.MalformedURLException;
import java.util.Vector;

import helma.xmlrpc.XmlRpc;
import helma.xmlrpc.XmlRpcClientRaw;
import helma.xmlrpc.XmlRpcException;
import javax.servlet.annotation.WebServlet;

//import com.stevesoft.pat.* ;

//@WebServlet("/test")
public class XmlRpcTester extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {

    // Set the Content-Type header
    res.setContentType("text/html");

    // Return early if this is a HEAD
    if (req.getMethod().equals("HEAD")) return;

    // Proceed otherwise
    PrintWriter out = res.getWriter();
    String submit = req.getParameter("submit");
    String server = req.getParameter("server");
    String request = req.getParameter("request");
    String protocol ;
    if ( req.isSecure() )
    { protocol = "https" ; }
    else
    { protocol = "http" ; }

    String servleturl = protocol+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+req.getServletPath() ;

    if ( submit == null || server == null || request == null ) {

	showForm( out, servleturl, "http://localhost:8080/xmlrpc/xmlrpchello" ) ;
    }
    else {

        try {

            	// Use the Apache Xerces SAX Driver
            	XmlRpc.setDriver("org.apache.xerces.parsers.SAXParser");

            	// Specify the server
            	XmlRpcClientRaw client = new XmlRpcClientRaw( server );

            	// Make a request and print the result
            	String result = client.execute( request ) ;

	showForm( out, servleturl, server ) ;
	showResult( out, request, result ) ;

        } catch (ClassNotFoundException e) {
            out.println("Could not locate SAX Driver");
        } catch (MalformedURLException e) {
            out.println(
                "Incorrect URL for XML-RPC server format: " +
                e.getMessage());
        } catch (XmlRpcException e) {
            out.println("XML-RPC Exception: " + e.getMessage());
        } catch (IOException e) {
            out.println("IO Exception: " + e.getMessage());
        }

    }

 }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    doGet(req, res);
  }

  private void showForm( PrintWriter out, String servleturl, String rpcserver ) {

	out.println( "<form method=\"POST\" action=\""+servleturl+"\">" ) ;
	out.println( "  <div align=\"left\">" ) ;
	out.println( "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"598\" colspan=\"2\" align=\"center\" bgcolor=\"#000000\"><b><font face=\"Arial\" color=\"#FFFFFF\">XML-RPC" ) ;
	out.println( "          Protocol Tester</font></b></td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"76\"></td>" ) ;
	out.println( "        <td width=\"520\">&nbsp;</td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"76\">&nbsp;<b>Server: </b></td>" ) ;
	out.println( "        <td width=\"520\">&nbsp;<input type=\"text\" name=\"server\" size=\"90\" value=\""+rpcserver+"\"></td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"76\"><b>&nbsp;Request:</b></td>" ) ;
	out.println( "        <td width=\"520\">&nbsp;<textarea rows=\"11\" name=\"request\" cols=\"62\"></textarea></td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"76\">&nbsp;</td>" ) ;
	out.println( "        <td width=\"520\"></td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"76\">&nbsp;</td>" ) ;
	out.println( "        <td width=\"520\">&nbsp;<input type=\"submit\" value=\"Submit\" name=\"submit\"></td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"598\" colspan=\"2\">" ) ;
	out.println( "          &nbsp;" ) ;
	out.println( "        </td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"598\" colspan=\"2\">" ) ;
	out.println( "          <hr>" ) ;
	out.println( "        </td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"596\" colspan=\"2\">&nbsp; </td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "    </table>" ) ;
	out.println( "  </div>" ) ;
	out.println( "</form>" ) ;

  }

  private void showResult( PrintWriter out, String xml_request, String xml_result ) {

      /*
	Transformer tx = new Transformer(true) ;
  	tx.add(new String[] {
       		"s/</&lt;/",
       		"s/>/&gt;<br>/"
    		});
      */


	out.println( "  <div align=\"left\">" ) ;
	out.println( "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"76\" valign=\"top\">&nbsp;<b>Request:</b></td>" ) ;
	out.println( "        <td width=\"520\">" ) ;
	out.println( "          <div align=\"left\">" ) ;
	out.println( "            <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bordercolor=\"#000000\">" ) ;
	out.println( "              <tr>" ) ;
	out.println( "                <td width=\"100%\" valign=top>" ) ;
	//out.println( "                  <pre>" ) ;
	//out.println( tx.replaceAll(xml_request) ) ;
	//out.println( "                  </pre>" ) ;
        out.println( xmlpreview( xml_request ) ) ;
	out.println( "              </tr>" ) ;
	out.println( "            </table>" ) ;
	out.println( "          </div>" ) ;
	out.println( "        </td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"76\">&nbsp;</td>" ) ;
	out.println( "        <td width=\"520\">&nbsp;</td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td width=\"76\" valign=\"top\">&nbsp;<b>Result:</b></td>" ) ;
	out.println( "        <td width=\"520\">" ) ;
	out.println( "          <div align=\"left\">" ) ;
	out.println( "            <table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bordercolor=\"#000000\">" ) ;
	out.println( "              <tr>" ) ;
	out.println( "                <td width=\"100%\" valign=top>" ) ;
	//out.println( "                  <pre>" ) ;
	//out.println(tx.replaceAll(xml_result) ) ;
	//out.println( "                  </pre>" ) ;
        out.println( xmlpreview( xml_result ) ) ;
	out.println( "              </tr>" ) ;
	out.println( "            </table>" ) ;
	out.println( "          </div>" ) ;
	out.println( "        </td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "      <tr>" ) ;
	out.println( "        <td colspan=\"2\" width=\"598\"></td>" ) ;
	out.println( "      </tr>" ) ;
	out.println( "    </table>" ) ;
	out.println( "  </div>" ) ;
  }


  private String xmlpreview( String xmlstr ) {

    StringBuffer strbuf = new StringBuffer( xmlstr ) ;
    StringBuffer result = new StringBuffer( 1024 ) ;

    for ( int i = 0 ; i < strbuf.length() ; i++ ) {
      char ch = strbuf.charAt( i ) ;
      if ( ch == '<' )
        result.append( "&lt;" ) ;
      else if ( ch == '>' )
        result.append( "&gt;<br>" ) ;
      else
        result.append( ch ) ;
    }

    return result.toString() ;
  }




}

