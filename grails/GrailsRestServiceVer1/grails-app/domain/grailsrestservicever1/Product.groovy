package grailsrestservicever1

class Product {
	
	String sku
	String name
	Double price
	
    static constraints = { 
		sku unique: true
	}
	static belongsTo = [vendor:Vendor]
}
