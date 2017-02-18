package hellorest

class Vendor {

	String name
	String stockSymbol
	String websiteURL
	
    static constraints = { 
		stockSymbol unique: true
	}

	static hasMany = [ products:Product ]
}
