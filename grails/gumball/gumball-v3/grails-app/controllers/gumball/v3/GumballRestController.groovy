package gumball.v3


import grails.rest.*
import grails.converters.*

class GumballRestController extends RestfulController {

    static responseFormats = ['json', 'xml']
    static machineSerialNum = "1234998871109"

    GumballRestController() {
        super(Gumball)
    }

    // HTTP GET - Gumball Machine Inventory
	def machineStatus() {

		def gumball = Gumball.findBySerialNumber( machineSerialNum )
		if ( gumball )
		{
			render gumball as JSON ;
		}
		else
		{
			render(contentType: "application/json") {
				Error( message: "Gumball Machine ${machineSerialNum} Not Configured." ) 
			}

		}

	}

    // HTTP PUT - Gumball Machine Inventory Update
	def updateInventory() {

		/* parse request using json slurper */
		def json = request.JSON
		println( json )
		println( json.countGumballs )

		def gumball = Gumball.findBySerialNumber( machineSerialNum )
		if ( gumball )
		{
			gumball.countGumballs = json.countGumballs ;
			gumball.save(flush:true) ;
			render gumball as JSON ;
		}
		else
		{
			render(contentType: "application/json") {
				Error( message: "Gumball Machine ${machineSerialNum} Not Configured." ) 
			}

		}

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
					Error( message: "Order ${params.id} Not Found." ) 
				}
			}
		}
		else
		{
			render(contentType: "application/json") {
				Error( message: "Order Number Missing." ) 
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
				Error( message: "System Error." ) 
			}
		}
					
	}

}
