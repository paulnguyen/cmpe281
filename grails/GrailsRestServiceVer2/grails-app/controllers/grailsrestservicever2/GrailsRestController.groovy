package grailsrestservicever2

import grails.converters.JSON

class GrailsRestController {

	def defaultAction = 'orderStatus'
	def String machineSerialNum = "1234998871109"

	def index() {
		redirect(action: 'orderStatus')
	}
	
	// HTTP GET -- Get Order Status
	def orderStatus() {
		
		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}		

		if ( params.id )
		{
			def ord = GumballOrder.findById(params.id)
			if ( ord )
			{
				render ord as JSON
			}
			else {
				render(contentType: "application/json") {
					error = "Order Not Found."
				}
			}
		}
		else
		{

			def gumball = Gumball.findBySerialNumber( machineSerialNum )
			if ( gumball )
			{
				render gumball as JSON ;
			}
			
		}

	}

	// HTTP POST -- Create a new Order
	def placeOrder() { 
		
		// dump out request object
		request.each { key, value ->
			println( "request: $key = $value")
		}

		// dump out params
		params?.each { key, value ->
			println( "params: $key = $value" )
		}

		def gumball = Gumball.findBySerialNumber( machineSerialNum )
		if ( gumball )
		{						
			if ( gumball.countGumballs > 0)
			{
					gumball.lock() // pessimistic lock
					gumball.countGumballs-- ;
					gumball.save(flush: true);
					def newOrder = new GumballOrder(orderstatus: "Order Placed") ;
					newOrder.save(flush: true) ;
					render newOrder as JSON ;
			} 
			else 
			{
				def newOrder = new GumballOrder(orderstatus: "Backed Ordered") ;
				newOrder.save(flush: true) ;
				render newOrder as JSON ;
			}
		}	
		else {
			render(contentType: "application/json") {
				error = "System Error."
			}
		}
					
	}

	
	}

