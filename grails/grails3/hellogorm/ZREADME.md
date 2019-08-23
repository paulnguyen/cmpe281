## Grails Resources

		http://docs.grails.org/latest/
		http://gorm.grails.org/6.0.x/hibernate/manual/#domainClasses
		http://gorm.grails.org/6.0.x/hibernate/manual/#constraints
		http://gorm.grails.org/6.0.x/hibernate/manual/#ormdsl
		http://sergiodelamo.es/log-sql-grails-3-app/

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
	 
	// Table and Column Mappings
	grails create-domain-class Book

	// Many-to-One and One-to-One
	grails create-domain-class Face0	
	grails create-domain-class Nose0	
	grails create-domain-class Face1	
	grails create-domain-class Nose1	
	grails create-domain-class Face2	
	grails create-domain-class Nose2	

	// Association Types
	grails create-domain-class Person1	
	grails create-domain-class Person2	

	// One-to-Many
	grails create-domain-class Author1
	grails create-domain-class Book1

	// Multiple One-to-Many
	grails create-domain-class Airport1
	grails create-domain-class Flight1
	grails create-domain-class Airport2
	grails create-domain-class Flight2

	// Many-to-Many
	grails create-domain-class Author2
	grails create-domain-class Book2	

	// Basic Collection Types
	grails create-domain-class Person3

	// Composition in GORM
	grails create-domain-class Person4
	grails create-domain-class Address4

	// Inheritance in GORM
	grails create-domain-class Content
	grails create-domain-class BlogEntry
	grails create-domain-class PodCast

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






