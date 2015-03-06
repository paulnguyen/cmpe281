class UrlMappings {
	
	static mappings = {
		
		"/order/$id?"(controller: "GrailsRest", parseRequest: true) {
			action = [GET: "orderStatus", POST: "placeOrder"]
		}

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
