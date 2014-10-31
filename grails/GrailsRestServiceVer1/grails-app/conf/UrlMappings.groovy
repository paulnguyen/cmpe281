class UrlMappings {
	
	static mappings = {
		
		/* REST API - XML VERSION
		"/api/$sku?"(controller: "ApiXML", parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		*/
	
		/* REST API - JSON VERSION */
		"/api/$sku?"(controller: "ApiJSON", parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
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
