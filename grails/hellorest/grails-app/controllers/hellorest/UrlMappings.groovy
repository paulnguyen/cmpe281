package hellorest

class UrlMappings {

    static mappings = {

        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        /* Simple Mappings */
        /*
        get "/data"(controller: 'API', action:'data')
        get "/search"(controller: 'API', action:'search')
        "/api/$sku?"(controller: "API", parseRequest: true) {
            action = [GET: "index", PUT: "update", DELETE: "delete", POST: "save"]
        }
        */

        /* Resource Driven Mappings */
        group "/api", {
            get "/data"(controller: 'API', action:'data')
            group "/vendor", {
                get "/search"(controller: 'API', action:'search')
            }            
            group "/product", {
                "/$sku?"(controller: "API", parseRequest: true) {
                    action = [GET: "index", PUT: "update", DELETE: "delete", POST: "save"]
                }
            }
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}

