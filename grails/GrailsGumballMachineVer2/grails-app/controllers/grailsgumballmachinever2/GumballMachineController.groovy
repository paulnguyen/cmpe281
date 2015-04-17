package grailsgumballmachinever2

import gumball.GumballMachine

class GumballMachineController {

    def String machineSerialNum = "1234998871109"
    def GumballMachine gumballMachine
    
    def index() {
		
		String VCAP_SERVICES = System.getenv('VCAP_SERVICES')
		
        if (request.method == "GET") {

            // search db for gumball machine
            def gumball = Gumball.findBySerialNumber( machineSerialNum )
            if ( gumball )
            {
                // create a default machine
                gumballMachine = new GumballMachine(gumball.modelNumber, gumball.serialNumber)
                System.out.println(gumballMachine.getAbout())
				
				// save in the session
				session.machine = gumballMachine
				
				// report a message to user
				flash.message = gumballMachine.getAbout()
	
				// display view
				render(view: "index")
	
			}
            else
            {
                flash.message = "Error! Gumball Machine Not Found!"
				render(view: "index")
            }

  
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

			System.out.println(gumballMachine.getAbout())
			
            if ( params?.event == "Insert Quarter" )
            {
                gumballMachine.insertCoin()
            }
            if ( params?.event == "Turn Crank" )
            {	
                gumballMachine.crankHandle();
				
				if ( gumballMachine.getCurrentState().equals("gumball.CoinAcceptedState") )
				{
					def gumball = Gumball.findBySerialNumber( machineSerialNum )
					if ( gumball )
					{
						// update gumball inventory
						// gumball.lock() // pessimistic lock
						if ( gumball.countGumballs > 0)
							gumball.countGumballs-- ;
						gumball.save(flush: true); // default optimistic lock
					}
				}
				
            }

			
            // report a message to user
            flash.message = gumballMachine.getAbout()

            // render view
            render(view: "index")
        }
        else {
            render(view: "/error")
        }
    }

}

