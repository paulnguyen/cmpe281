grails.project.work.dir = 'target'

grails.project.dependency.resolution = {

    inherits "global"
    log "warn"

    repositories {
        grailsCentral()
    }

    dependencies {
        compile('org.hibernate:hibernate-core:3.6.10.Final') {
            exclude group:'commons-logging', name:'commons-logging'
            exclude group:'commons-collections', name:'commons-collections'
            exclude group:'org.slf4j', name:'slf4j-api'
            exclude group:'xml-apis', name:'xml-apis'
            exclude group:'dom4j', name:'dom4j'
        }
        compile( 'org.hibernate:hibernate-commons-annotations:3.2.0.Final' ){
            excludes 'slf4j-api'
        }
        
        compile('org.hibernate:hibernate-validator:4.1.0.Final') {
            exclude group:'commons-logging', name:'commons-logging'
            exclude group:'commons-collections', name:'commons-collections'
            exclude group:'org.slf4j', name:'slf4j-api'
        }

        runtime 'org.javassist:javassist:3.16.1-GA'
        runtime 'antlr:antlr:2.7.7'
        runtime('dom4j:dom4j:1.6.1') {
            exclude group:'xml-apis', name:'xml-apis'
        }
        runtime('org.hibernate:hibernate-ehcache:3.6.10.Final') {
             exclude group:'commons-logging', name:'commons-logging'
             exclude group:'commons-collections', name:'commons-collections'
             exclude group:'org.slf4j', name:'slf4j-api'
             exclude group:'xml-apis', name:'xml-apis'
             exclude group:'dom4j', name:'dom4j'
             exclude group:'org.hibernate', name:'hibernate-core'
             exclude group:'net.sf.ehcache', name:'ehcache'
             exclude group:'net.sf.ehcache', name:'ehcache-core'
        }
    }

}
