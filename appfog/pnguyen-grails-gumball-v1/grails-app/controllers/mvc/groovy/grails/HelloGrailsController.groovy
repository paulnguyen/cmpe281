package mvc.groovy.grails

class HelloGrailsController {
    def index() {
        HelloWorld javaObject = new HelloWorld() ;
		String VCAP_SERVICES = System.getenv('VCAP_SERVICES')	
		
		//println "VCAP_SERVICES: ${System.getenv('VCAP_SERVICES')}"
		String output = "<p>"
		output += "Hello Grails! ==> " +  javaObject.getMessage() ;
		output += "</p><hr/>"
		output += "<p>" + VCAP_SERVICES + "</p>"
		
        render  output ;
    }
}
