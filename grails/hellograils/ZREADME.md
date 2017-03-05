## Grails Resources

		http://guides.grails.org/creating-your-first-grails-app/guide/index.html
		https://github.com/grails-guides/creating-your-first-grails-app


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
	 
	grails create-domain-class Vehicle
	grails create-domain-class Make
	grails create-domain-class Model
	
	grails create-controller Home
	grails create-controller Vehicle
	grails create-controller Make
	grails create-controller Model
	
	grails run-app
	grails run-app --port=8090
	grails test run-app
	
	grails test-app
	grails test-app -unit
	grails test-app -integration

## Grails Docker (Tomcat 7)

	https://hub.docker.com/_/tomcat/
	
	FROM tomcat:7
	EXPOSE 8080
	ENV CATALINA_OPTS -Djava.awt.headless=true -Dfile.encoding=UTF-8 -server -Xms512m -Xmx1300m -XX:PermSize=256m -XX:MaxPermSize=512m -XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC
	ADD ./build/libs/hellograils-1.0.war /usr/local/tomcat/webapps/hellograils.war
	ADD ./tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

## Grails Docker (Grails 3)

	https://hub.docker.com/r/mozart/grails/

	FROM mozart/grails:3
	COPY . /app
	RUN grails dependency-report
	ENTRYPOINT ["grails"]
	CMD ["run"]

## Grails Docker (Boot)

	https://hub.docker.com/_/openjdk/

	FROM openjdk:8
	EXPOSE 8080
	ADD ./build/libs/hellograils-1.0.war /srv/hellograils.war
	CMD java -jar /srv/hellograils.war

## Grails Docker (Start-Up)

	docker build -t hellograils .
	docker run --name grails-mysql -e MYSQL_ROOT_PASSWORD=welcome -dt mysql
	docker run --name hellograils --link grails-mysql:mysql -p 8080:8080 -dt hellograils

## Grails H2 DB Console

	http://localhost:8080/dbconsole 
	jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE
	
	
## Grails Console

	grails console
	
	---
	
	import hellograils.Vehicle 
	
	def vehicles = Vehicle.list()
	println vehicles.size()

	def pickup = Vehicle.findByName("Pickup")
	println pickup.name
	println pickup.make.name
	println pickup.model.name

	def nissan = Make.findByName("Nissan")
	def nissans = Vehicle.findAllByMake(nissan)
	println nissans.size()


## Environment

	environments:
	    development:
	        dataSource:
	            dbCreate: create-drop
	            url: jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE
	    test:
	        dataSource:
	            dbCreate: update
	            driverClassName: com.mysql.jdbc.Driver   
	            dialect: org.hibernate.dialect.MySQL5InnoDBDialect
	            username: admin
	            password: mysql
	            url: jdbc:mysql://127.0.0.1:3306/test
	    production:
	        dataSource:
	            dbCreate: none
	            url: jdbc:h2:./prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE
	            properties:
	                jmxEnabled: true
	                initialSize: 5
	                maxActive: 50
	                minIdle: 5
	                maxIdle: 25
	                maxWait: 10000
	                maxAge: 600000
	                timeBetweenEvictionRunsMillis: 5000
	                minEvictableIdleTimeMillis: 60000
	                validationQuery: SELECT 1
	                validationQueryTimeout: 3
	                validationInterval: 15000
	                testOnBorrow: true
	                testWhileIdle: true
	                testOnReturn: false
	                jdbcInterceptors: ConnectionState
	                defaultTransactionIsolation: 2 # TRANSACTION_READ_COMMITTED

## Gradle Build Dependencies

	dependencies {
	    compile "org.springframework.boot:spring-boot-starter-logging"
	    compile "org.springframework.boot:spring-boot-autoconfigure"
	    compile "org.grails:grails-core"
	    compile "org.springframework.boot:spring-boot-starter-actuator"
	    compile "org.springframework.boot:spring-boot-starter-tomcat"
	    compile "org.grails:grails-dependencies"
	    compile "org.grails:grails-web-boot"
	    compile "org.grails.plugins:cache"
	    compile "org.grails.plugins:scaffolding"
	    compile "org.grails.plugins:hibernate5"
	    compile "org.hibernate:hibernate-core:5.1.2.Final"
	    compile "org.hibernate:hibernate-ehcache:5.1.2.Final"
	    console "org.grails:grails-console"
	    profile "org.grails.profiles:web"
	    runtime "com.bertramlabs.plugins:asset-pipeline-grails:2.11.6"
	    runtime "com.h2database:h2"
	    runtime "mysql:mysql-connector-java:5.1.40"
	    testCompile "org.grails:grails-plugin-testing"
	    testCompile "org.grails.plugins:geb"
	    testRuntime "org.seleniumhq.selenium:selenium-htmlunit-driver:2.47.1"
	    testRuntime "net.sourceforge.htmlunit:htmlunit:2.18"
	}


