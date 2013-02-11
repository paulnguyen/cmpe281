package pnguyen.grails.webservices

import grails.converters.XML
import grails.converters.JSON

class ApiController {

	def defaultAction = 'show'

	def index() {
		redirect(action: 'show')
	}
	
	// HTTP GET
	def show() {

		if ( params.sku )
		{
			def p = Product.findBySku(params.sku)
			if ( p )
			{
				render p as XML
			}
			else {
				def all = Product.list()
				render all as XML
			}
		}
		else
		{
			def p = Product.list()
			render p as XML
		}

	}

	// HTTP PUT
	def update() { }

	// HTTP POST
	def save() {

		println( params )
		def p = new Product(params.product)
		if (p.save()) { render p as XML } else { render p.errors }

	}

	// HTTP DELETE
	def delete() { }

}
