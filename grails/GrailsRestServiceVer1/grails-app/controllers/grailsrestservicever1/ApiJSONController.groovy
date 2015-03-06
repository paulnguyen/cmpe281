package grailsrestservicever1

import grails.converters.JSON

class ApiJSONController {

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
				render p as JSON
			}
			else {
				//def all = Product.list()
				//render all as JSON
				render(contentType: "application/json") {
					error = "Product Not Found."
				}
			}
		}
		else
		{
			def p = Product.list()
			render p as JSON
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
		println( "NEW NAME: " + request.JSON.name )
		println( "NEW PRICE: " + request.JSON.price )
		println( "NEW SKU: " + request.JSON.sku )

		if ( params.sku )
		{
			println ( "Updating SKU: " + params.sku )
			
			def p = Product.findBySku(params.sku)
			if ( p )
			{
				if (request.JSON.name != null) p.name = request.JSON.name
				if (request.JSON.price != null) p.price = request.JSON.price.toDouble()
				if (request.JSON.sku != null) p.sku = request.JSON.sku
				try {
					p.save(flush: true)
					render p as JSON				
				}
				catch (e) {
					//println p.errors
					println e
					render(contentType: "application/json") {
						error = "Product Update Error."
					}
					//render(contentType: "application/json")
					//{
					//	faultcode = 28763
					//	faultmessage = "Product Update Error."
					//} 
				}
			}
		}
		else 
		{
			render(contentType: "application/json") {
				error = "No SKU in Update Request."
			}
			/*
			render(contentType: "application/json")
			{
				faultcode = 28983
				faultmessage = "No SKU in Update Request."
				
			}
			*/
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
		//def request = request.inputStream.getText()
		//println( request )
		
		/* parse request using json slurper */
		def json = request.JSON
		println( request.JSON )
		println( request.JSON.id )
		println( request.JSON.name )
		println( request.JSON.price )
		println( request.JSON.sku )
		println( request.JSON.vendor.id )
		
		def p = new Product()
		p.id = request.JSON.id.toLong()
		p.name = request.JSON.name
		p.price = request.JSON.price.toDouble()
		p.sku = request.JSON.sku
		def v = Vendor.findById( request.JSON.vendor.id.toLong() )
		p.vendor = v
		try {
			p.save(flush: true)
		}
		catch (e) {
			
			println p.errors
			println e
			
			render(contentType: "application/json") {
				error = "Error Creating Product."
			}
		}
	
		def saved = Product.findBySku(p.sku)
		if ( saved )
		{
			render saved as JSON
		}
		else
		{
			render(contentType: "application/json") {
				error = "Error Creating Product."
			}
			
			/* auto  */
			//render p.errors as JSON
			
			/* custom 
			render(contentType:"application/json")
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
			*/
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
				
				render(contentType: "application/json") {
					success = "Product Deleted!"
				}
				
				/*
				render(contentType:"application/json")
				{
					success {
							code('20000')
							message('Product deleted!')
					}
				}
				*/
			}
			else {
				
				render(contentType: "application/json") {
					error = "Product not found!"
				}
				
				/*
				render(contentType:"application/json")
				{
					fault {
							code('18876')
							message('Product not found!')
					}
				}
				*/
			}
		}		
		
	}

}


/**
 
 	// Get List: http://localhost:8080/GrailsRestServiceVer1/api/
 
    [
       {
           "class": "grailsrestservicever1.Product",
           "id": 1,
           "name": "MacBook Pro 13",
           "price": 1500,
           "sku": "AAPL123456789",
           "vendor":
           {
               "class": "grailsrestservicever1.Vendor",
               "id": 1
           }
       },
       {
           "class": "grailsrestservicever1.Product",
           "id": 2,
           "name": "Oracle Database 11g Standard",
           "price": 5000,
           "sku": "ORCL123456789",
           "vendor":
           {
               "class": "grailsrestservicever1.Vendor",
               "id": 2
           }
       }
    ]

 
 	// Get by SKU: http://localhost:8080/GrailsRestServiceVer1/api/AAPL123456789
 
    {
       "class": "grailsrestservicever1.Product",
       "id": 1,
       "name": "MacBook Pro 13",
       "price": 1500,
       "sku": "AAPL123456789",
       "vendor":
       {
           "class": "grailsrestservicever1.Vendor",
           "id": 1
       }
    }

	// Put Update: http://localhost:8080/GrailsRestServiceVer1/api/AAPL123456789

   {
       "name": "MacBook Pro 13",
       "price": 1599,
       "sku": "AAPL123456789",
    }
 
 
	// Post Create: http://localhost:8080/GrailsRestServiceVer1/api

    {
       "class": "grailsrestservicever1.Product",
       "id": 10,
       "name": "MacBook Pro 17",
       "price": 4500,
       "sku": "AAPL123459999",
       "vendor":
       {
          "class": "grailsrestservicever1.Vendor",
          "id": 1
       }
    }

	// Put Delete: http://localhost:8080/GrailsRestServiceVer1/api/AAPL123459999


 **/

