
## Grails Resources

		http://docs.grails.org/latest/guide/webServices.html
		http://docs.grails.org/latest/guide/theWebLayer.html#restfulMappings
		http://docs.grails.org/latest/guide/theWebLayer.html#urlmappings

## Local Environment Setup

		sdk ls groovy
		sdk ls grails
	   	sdk install groovy 2.4.7
	   	sdk install grails 3.2.5
	   	sdk current

	   	grails --version
	    
		| Grails Version: 3.2.5
		| Groovy Version: 2.4.7
		| JVM Version: 1.8.0_112
	    
## Grails Commands
	 
	grails create-domain-class Book
	grails create-domain-class Author

	grails create-controller BooksController

	grails run-app
	grails run-app --port=8090
	grails test run-app
	
	grails test-app
	grails test-app -unit
	grails test-app -integration


## Grails H2 DB Console

	http://localhost:8080/dbconsole 
	jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE
	

## Grails Console

	grails console
	grails test console





