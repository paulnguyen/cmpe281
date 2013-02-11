/**
 * <b><code>HelloHandler</code></b> is a simple handler that can
 *   be registered with an XML-RPC server.
 *
 * @version 1.0
 */
package edu.sjsu.cmpe281 ;

import java.util.Vector ;
import java.util.Hashtable ;

public class XmlRpcHelloHandler {

	public String hello ( String name, String number ) {
		return "XML-RPC Says Hello " + name ;
	}

	public Vector getVector () {
		Vector data = new Vector() ;

		data.add( "12345679" ) ;
		data.add( "John Smith" ) ;
		data.add( "1234-5678-6789-0100" ) ;
		data.add( "May 05 2002" ) ;
		data.add( "Active" ) ;

		return data ;
	}


	public Hashtable getHashtable() {
		Hashtable data = new Hashtable() ;

		data.put( "UID", "123456789" ) ;
		data.put( "SUPPORT_ID", "123987" ) ;
		data.put( "CHIP_TECH_SPEC", "UPC-109883" ) ;
		data.put( "USER_STATE", "NEW" ) ;
		data.put( "CARD_EXPIRATION_MM_DD_YYYY", "05-05-2002" ) ;

		return data ;
	}


}