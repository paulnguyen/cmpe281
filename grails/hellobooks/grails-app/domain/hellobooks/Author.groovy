package hellobooks

import grails.rest.*


@Resource(uri='/authors', formats=['json', 'xml'])
class Author {
	
    static hasMany = [books:Book]
    String name

}