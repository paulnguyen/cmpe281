package grailsgumballmachine

class GumballOrder {

	String	orderstatus ;
	
	static mapping = {
		id generator: 'increment'
	}
 
}
