package gumball.v3

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")


        "/order/$id?"(controller: "GumballRest", parseRequest: true) {
            action = [GET: "orderStatus", POST: "placeOrder"]
        }

        get "/gumball"(controller: 'GumballRest', action:'machineStatus')
        put "/gumball"(controller: 'GumballRest', action:'updateInventory')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
