package grailshelloworldver1

class HeloWorldController {

    def index() { 
		render "<h1>Hello World!</h1>"
		
		//render(contentType: "application/json") {
		//	hello = "world"
		//}
	}
}
