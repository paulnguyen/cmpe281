package mvc.groovy.grails

class HelloGrailsController {
    def index() {
        HelloWorld javaObject = new HelloWorld() ;
		
		String VCAP_SERVICES = System.getenv('VCAP_SERVICES')	
		println "VCAP_SERVICES: ${System.getenv('VCAP_SERVICES')}"
		
        render "Hello Grails! " +  javaObject.getMessage() ;
    }
}
