package grailsrestservicever1

class LoadDataController {

	def index() {
		def vendor1 = new Vendor(name: "Apple", stockSymbol: "AAPL", websiteURL: "http://www.apple.com").addToProducts(
		sku: "AAPL123456789", name: "MacBook Pro 13", price:1500.00
		)
		vendor1.save()

		def vendor2 = new Vendor(name: "Oracle", stockSymbol: "ORCL", websiteURL: "http://www.oracle.com").addToProducts(
		sku: "ORCL123456789", name: "Oracle Database 11g Standard", price:5000.00
		)
		vendor2.save()
		
		new Book(title:"The Stand").save()
		new Book(title:"The Shining").save()

		render "done!"
	}
}
