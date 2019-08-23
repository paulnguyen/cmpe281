package hellograils

/*
class Vehicle {

 	String name 
	String make
    String model

    static constraints = { 
        name maxSize: 255
        make inList: ['Ford', 'Chevrolet', 'Nissan']
        model nullable: true
    }

}
*/

class Vehicle {

    Integer year

    String name
    Model model
    Make make

    static constraints = {
        year min: 1900
        name maxSize: 255
    }
}

/*

def vehicle = Vehicle.findByName('pickup')
if(vehicle) {
    println vehicle.model // e.g, 'Titan'
    println vehicle.make // e.g, 'Nissan'
}

*/