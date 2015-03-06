package grailsrestservicever2

class GumballOrder {

	String	orderstatus ;
	
	static mapping = {
		id generator: 'increment'
	}
 
}
