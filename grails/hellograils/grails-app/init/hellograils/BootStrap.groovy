package hellograils

class BootStrap {

    def init = { servletContext ->

        def nissan = new Make(name: "Nissan").save()
        def ford = new Make(name: "Ford").save()

        def titan = new Model(name: "Titan", make: nissan).save()
        def leaf = new Model(name: "Leaf", make: nissan).save()
        def windstar = new Model(name: "Windstar", make: ford).save()

        new Vehicle(name: "Pickup",  make: nissan, model: titan, year: 2012).save()
        new Vehicle(name: "Economy", make: nissan, model: leaf, year: 2014).save()
        new Vehicle(name: "Minivan", make: ford, model: windstar, year: 1990).save()
    }
    
    def destroy = {

    }
}