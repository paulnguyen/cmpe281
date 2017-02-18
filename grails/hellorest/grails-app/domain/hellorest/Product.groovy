package hellorest

class Product {

	String sku
	String name
	Double price    

	static belongsTo = [ vendor:Vendor ]

    static constraints = {
    	sku blank:false
        name blank:false
        price range:0.0..10000.00
    }

}