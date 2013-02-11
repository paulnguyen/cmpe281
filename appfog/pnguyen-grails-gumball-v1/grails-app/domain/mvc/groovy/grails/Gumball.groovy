package mvc.groovy.grails

class Gumball {

    String modelNumber
    String serialNumber
    Integer countGumballs

    static constraints = {
        serialNumber(unique: true)
    }
}
