package hellobooks

import grails.rest.*

/* Makes API Read Only */
//@Resource(uri='/books', readOnly=true)

/* Uses Annotation to Map URL and Set Supported Content Formats */
@Resource(uri='/books', formats=['json', 'xml'])

/* URI Mapping in Controllers UrlMappings.groovy */
//@Resource( formats=['json', 'xml'] )

class Book {

	static belongsTo = Author
    static hasMany = [authors:Author]	

    String title
    Date releaseDate
    String ISBN

    static constraints = {
        title blank:false
    }

}

