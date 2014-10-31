package grailsrestservicever1

import grails.converters.XML

class ApiXMLController {

	def defaultAction = 'show'

	def index() {
		redirect(action: 'show')
	}
	
	// HTTP GET
	def show() {
		
		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}		

		if ( params.sku )
		{
			def p = Product.findBySku(params.sku)
			if ( p )
			{
				render p as XML
			}
			else {
				def all = Product.list()
				render all as XML
			}
		}
		else
		{
			def p = Product.list()
			render p as XML
		}

	}

	// HTTP PUT
	def update() { 
		
		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}
		
		// print-out request
		println( "NEW NAME: " + request.XML.name )
		println( "NEW PRICE: " + request.XML.price )
		println( "NEW SKU: " + request.XML.sku )

		if ( params.sku )
		{
			println ( "Updating SKU: " + params.sku )
			
			def p = Product.findBySku(params.sku)
			if ( p )
			{
				if (!request.XML.name.isEmpty()) p.name = request.XML.name
				if (!request.XML.price.isEmpty()) p.price = request.XML.price.toDouble()
				if (!request.XML.sku.isEmpty()) p.sku = request.XML.sku
				try {
					p.save(flush: true)
					render p as XML				
				}
				catch (e) {
					println p.errors
					println e
					render(contentType:"text/xml")
					{
						fault {
								code('28763')
								message('Product Update Error.')
								errors {
									p.errors.allErrors.each {
										error(it)
									}
								}
						}
					}
				}

			}
		}
		else 
		{
			render(contentType:"text/xml")
			{
				fault {
						code('28983')
						message('No SKU in Update Request.')
				}
			}
		}
				
	}

	// HTTP POST
	def save() {

		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}
		
		/* alternate way to dump request text */
		//def requestXML = request.inputStream.getText()
		//println( requestXML )
		
		/* parse request using xml slurper */
		//println( request.XML )
		println( request.XML.@id )
		println( request.XML.name )
		println( request.XML.price )
		println( request.XML.sku )
		println( request.XML.vendor.@id )
		
		def p = new Product()
		p.id = request.XML.@id.toLong()
		p.name = request.XML.name
		p.price = request.XML.price.toDouble()
		p.sku = request.XML.sku
		def v = Vendor.findById( request.XML.vendor.@id.toLong() )
		p.vendor = v
		try {
			p.save(flush: true)
		}
		catch (e) {
			println p.errors
			println e
		}
	
		def saved = Product.findBySku(p.sku)
		if ( saved )
		{
			render saved as XML
		}
		else
		{
			/* auto xml */
			//render p.errors as XML
			
			/* custom xml */
			render(contentType:"text/xml")
			{
				fault {
						code('19876')
                        message('Error Creating Product.')	
						errors {	
							p.errors.allErrors.each {
								error(it)
							}
						}
				}
			}
		}

	}

	// HTTP DELETE
	def delete() { 
		
		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}
		
		if ( params.sku )
		{
			def p = Product.findBySku(params.sku)
			if ( p )
			{
				p.delete(flush: true)
				render(contentType:"text/xml")
				{
					success {
							code('20000')
							message('Product deleted!')
					}
				}
			}
			else {
				render(contentType:"text/xml")
				{
					fault {
							code('18876')
							message('Product not found!')
					}
				}
			}
		}		
		
	}

}


/**

// Get list: http://localhost:8080/GrailsRestServiceVer1/api 
 
<?xml version="1.0" encoding="UTF-8"?>
<list>
	<product id="1">
		<name>MacBook Pro 13</name>
		<price>1500.0</price>
		<sku>AAPL123456789</sku>
		<vendor id="1"/>
	</product>
	<product id="2">
		<name>Oracle Database 11g Standard</name>
		<price>5000.0</price>
		<sku>ORCL123456789</sku>
		<vendor id="2"/>
	</product>
</list>

// Put Update: http://localhost:8080/GrailsRestServiceVer1/api/AAPL123456789
<product>
		<price>3500.0</price>
</product>

// Post Create: http://localhost:8080/GrailsRestServiceVer1/api  
 
<product id="3">
		<name>MacBook Pro 17</name>
		<price>2500.0</price>
		<sku>PPPL123456789</sku>
		<vendor id="1"/>
</product>

// Delete: http://localhost:8080/GrailsRestServiceVer1/api/PPPL123456789  
 
 **/

