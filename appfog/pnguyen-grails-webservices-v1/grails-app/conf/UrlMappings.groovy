class UrlMappings {

	static mappings = {

		/*
		"/product/$sku"(controller: "api", parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		*/

		"/api/$sku?"(controller: "api", parseRequest: true) {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}

						
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
