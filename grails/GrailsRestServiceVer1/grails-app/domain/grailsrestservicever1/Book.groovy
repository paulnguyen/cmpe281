package grailsrestservicever1

import grails.rest.*

@Resource(uri='/books',formats=['json', 'xml']) 
class Book {
	String title
	static constraints = { title blank:false } 
}