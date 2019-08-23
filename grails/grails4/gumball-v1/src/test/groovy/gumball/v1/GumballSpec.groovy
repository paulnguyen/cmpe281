package gumball.v1

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class GumballSpec extends Specification implements DomainUnitTest<Gumball> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
