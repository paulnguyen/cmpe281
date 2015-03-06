package grailsgumballmachinever1

import gumballstate.GumballMachine

class GumballBadLockController {

  def String machineSerialNum = "1234998871109"
    def GumballMachine gumballMachine
    
    def index() {
		
		String VCAP_SERVICES = System.getenv('VCAP_SERVICES')
		
		def sessionid = session.id 
		println ( "Session ID: $sessionid" ) ;
		
        if (request.method == "GET") {

            // search db for gumball machine
            def gumball = Gumball.findBySerialNumber( machineSerialNum )
            if ( gumball )
            {
                // create a default machine
                gumballMachine = new GumballMachine(gumball.countGumballs)
                gumballMachine.setModelNumber(gumball.modelNumber)
                gumballMachine.setSerialNumber(gumball.serialNumber)
                System.out.println(gumballMachine)
            }
            else
            {
                // create a default machine
                gumballMachine = new GumballMachine(5);
                System.out.println(gumballMachine)
            }

            // save in the session
            session.machine = gumballMachine
			
            // report a message to user
            flash.message = gumballMachine.toString() 
			flash.sessionid = session.id ;

            // display view
            render(view: "index")

        }
        else if (request.method == "POST") {

            // dump out request object
            request.each { key, value ->
                println( "request: $key = $value")
            }

            // dump out params
            params?.each { key, value ->
                println( "params: $key = $value" )
            }

            // get machine from session
            gumballMachine = session.machine
            System.out.println(gumballMachine)
			
            if ( params?.event == "Insert Quarter" )
            {
                gumballMachine.insertQuarter()
            }
            if ( params?.event == "Turn Crank" )
            {	
				def before = gumballMachine.getCount() ;
                gumballMachine.turnCrank();
				def after = gumballMachine.getCount() ;
				
				if ( after != before )
				{
					def gumball = Gumball.findBySerialNumber( machineSerialNum )
					//gumball.lock() // pessimistic lock
					Thread.currentThread().sleep(10000)
					if ( gumball )
					{
						// update gumball inventory
						println( "Updating DB with new Inventory of: $after")
						gumball.countGumballs = after ;
						gumball.save(flush: true); // default optimistic lock
					}
				}
				
            }

			
            // report a message to user
            flash.message = gumballMachine.toString() 
			flash.sessionid = session.id ;
			
            // render view
            render(view: "index")
        }
        else {
            render(view: "/error")
        }
    }

			
	
}
