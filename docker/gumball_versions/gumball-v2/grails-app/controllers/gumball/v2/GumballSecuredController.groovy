package gumball.v2

import gumball.GumballMachine
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;

class GumballSecuredController {

	def String machineSerialNum = "1234998871109"
	def String secretKey = "kwRg54x2Go9iEdl49jFENRM12Mp711QI"
	def GumballMachine gumballMachine
	def String msg 
	def hash 
	
	// REF:  http://www.jokecamp.com/blog/examples-of-creating-base64-hashes-using-hmac-sha256-in-different-languages/#groovy
	def hmac_sha256(String secretKey, String data) {
	 try {
		Mac mac = Mac.getInstance("HmacSHA256")
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256")
		mac.init(secretKeySpec)
		byte[] digest = mac.doFinal(data.getBytes())
		return digest
	   } catch (InvalidKeyException e) {
		throw new RuntimeException("Invalid key exception while converting to HMac SHA256")
	  }
	}
	
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
			}
			else
			{
				flash.message = "Error! Gumball Machine Not Found!"
				render(view: "index")
			}

			// don't save in the session
			// session.machine = gumballMachine
			
			// send machine state to client (instead)
			flash.state = gumballMachine.getCurrentState() ;
			flash.model = gumball.modelNumber ;
			flash.serial = gumball.serialNumber ;
			
			// get system date
			flash.ts = System.currentTimeMillis().toString();
			msg = flash.state + "|" + flash.model + "|" + flash.serial + "|" + flash.ts + "|" + secretKey
			hash = hmac_sha256(secretKey, msg)
			println ( "MSG: " + msg ) 
			println ( "HASH: " + hash.encodeBase64() ) 
			flash.hash = hash.encodeBase64()
			
			// report a message to user
			flash.message = gumballMachine.getAbout()

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
			
			
			// don't get machine from session
			// gumballMachine = session.machine

			// restore machine to client state (instead)
			def state = params?.state
			def modelNum = params?.model
			def serialNum = params?.serial
			def tsString = params?.ts
			
			// check hash and time stamp
			def long ts = Long.parseLong( tsString )
			def long cts = System.currentTimeMillis() 
			def long diff = cts - ts 
			println ( diff/1000 ) // seconds
			def hash1 = params?.hash
			def String msg = state + "|" + modelNum + "|" + serialNum + "|" + tsString + "|" + secretKey
			def hashBytes = hmac_sha256(secretKey, msg)
			def hash2 = hashBytes.encodeBase64().toString()
			println ( "MSG: " + msg )
			println ( "HASH1: " + hash1 )
			println ( "HASH2: " + hash2 )
			
			def invalidTS = ((diff/1000) > 120)
			def invalidHASH = (hash1 != hash2) 
			println( "invalid ts: " + invalidTS )
			println( "invalid hash: " + invalidHASH )
			
			if ( invalidTS || invalidHASH )
			{
				// send machine state to client
				flash.state = state
				flash.model = modelNum ;
				flash.serial = serialNum ;
				
				// report a message to user
				flash.message = "*** SESSION INVALID ***"
			}
			else
			{
				gumballMachine = new GumballMachine(modelNum, serialNum) ;
				gumballMachine.setCurrentState(state) ;
				
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
							// gumball.lock() // pessimistic lock
							if ( gumball.countGumballs > 0)
								gumball.countGumballs-- ;
							gumball.save(flush: true); // default optimistic lock
						}
					}	
				}
				
				// send machine state to client
				flash.state = gumballMachine.getCurrentState() 
				flash.model = modelNum 
				flash.serial = serialNum 
				
				// report a message to user
				flash.message = gumballMachine.getAbout()

				// get system date
				flash.ts = System.currentTimeMillis().toString();
				msg = flash.state + "|" + flash.model + "|" + flash.serial + "|" + flash.ts + "|" + secretKey
				hash = hmac_sha256(secretKey, msg)
				println ( "MSG: " + msg )
				println ( "HASH: " + hash.encodeBase64() )
				flash.hash = hash.encodeBase64()

			}

			// render view
			render(view: "index")
		}
		else {
			render(view: "/error")
		}
	}

}

