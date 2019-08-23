package hellorest


import grails.rest.*
import grails.converters.*
import grails.transaction.*


class APIController extends RestfulController {

    static responseFormats = ['json', 'xml']

    APIController() {
        super(Product)
    }

	def data() {

		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}	

		def vendor1 = new Vendor(name: "Apple", stockSymbol: "AAPL", websiteURL: "http://www.apple.com")
		vendor1.addToProducts( sku: "AAPL123456789", name: "MacBook Pro 13", price:1500.00 )
		vendor1.save()

		def vendor2 = new Vendor(name: "Oracle", stockSymbol: "ORCL", websiteURL: "http://www.oracle.com")
		vendor2.addToProducts( sku: "ORCL123456789", name: "Oracle Database 11g Standard", price:5000.00 )
		vendor2.save()

		render(status: 201, text: "Data Loaded!" )
	}

	// Get Product(s)
	def index() {

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
				//render(status: 404, text: "Product not fournd ${params.sku}" )
				render(contentType: "application/json") {	
					Error(message: "Product not fournd ${params.sku}" )
				}
			}
		}
		else
		{
			def p = Product.list()
			render p as JSON
		}
	}

	// Search Vendors
   def search(String q, Integer max ) { 

   		println( "q: $q" )
   		println( "max: $max" )

	    if(q) {
	        def query = Vendor.where { 
	            name ==~ "%${q}%"
	        }
	        respond query.list(max: Math.min( max ?: 10, 100)) 
	    }
	    else {
	        respond([]) 
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
		
		/* parse request using json slurper */
		def json = request.JSON
		println( json )
		println( json.name )
		println( json.price )
		println( json.sku )
		println( json.vendor.id )
		
		def p = new Product()
		p.name = json.name
		p.price = json.price.toDouble()
		p.sku = json.sku
		def v = Vendor.findById( json.vendor.id.toLong() )
		p.vendor = v
		try {
			p.save(flush: true)
		}
		catch (e) {		
			println p.errors
			println e
			render(contentType: "application/json") {	
				Error( message: "Error Creating Product." )
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
				Error( message: "Error Creating Product." )
			}
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
		def json = request.JSON
		println( "NEW NAME: " + json.name )
		println( "NEW PRICE: " + json.price )
		println( "NEW SKU: " + json.sku )

		if ( params.sku )
		{
			println ( "Updating SKU: " + params.sku )
			
			def p = Product.findBySku(params.sku)
			if ( p )
			{
				if (json.name != null) p.name = json.name
				if (json.price != null) p.price = json.price.toDouble()
				if (json.sku != null) p.sku = json.sku
				try {
					p.save(flush: true)
					render p as JSON				
				}
				catch (e) {

					// console print errors
					println p.errors
					println e

					// render simple json error
					// render(contentType: "application/json") {
					// 	Error( message: "Product Update Error." )
					// }

					// render json error with app error code
					render(contentType: "application/json")
					{
						Error( 
							code: 28763,
							message: "Error Creating Product." 
						)
					} 
				}
			}
		}
		else 
		{
			// render simple json error
			// render(contentType: "application/json") {
			// 	Error( message: "No SKU in Update Request." )
			// }
			
			// render json error with app error code
			render(contentType: "application/json")
			{
				Error( 
					code: 28983,
					message: "No SKU in Update Request."
				)
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

				// render simple json message
				// render(contentType: "application/json") {
				// 	Success( message: "Product Deleted!" )
				// }

				// render json message with app code
				render(contentType: "application/json")
				{
					Success( 
						code: 20000,
						message: "Product Deleted!" 
					)
				} 
			}
			else {

				// render simple json message
				// render(contentType: "application/json") {
				// 	Error( message: "Product not found!" )
				// }

				// render json message with app code
				render(contentType: "application/json")
				{
					Error( 
						code: 18876,
						message: "Product not found!"
					)
				} 
			}
		}		
		
	}

}



/* 

Groovy Console Test

import hellorest.*
import grails.converters.*

def product1 = new Product( sku: "AAPL123456789", name: "MacBook Pro 13", price:1500.00 )
product1.save( flush:true )
println product1 as JSON
Product.list()

def vendor1 = new Vendor(name: "Apple", stockSymbol: "AAPL", websiteURL: "http://www.apple.com")
vendor1.save( flush:true )
println vendor1 as JSON
Vendor.list()

def vendor2 = new Vendor(name: "Oracle", stockSymbol: "ORCL", websiteURL: "http://www.oracle.com")
vendor2.addToProducts( sku: "ORCL123456789", name: "Oracle Database 11g Standard", price:5000.00 )
vendor2.save()
println vendor1 as JSON
Vendor.list()

*/


