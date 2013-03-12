class MysqlConnectorjGrailsPlugin {
    def version = "5.1.12"
    def grailsVersion = "1.2.0 > *"
    def dependsOn = [:]
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def author = "Marcel Overdijk"
    def authorEmail = "marceloverdijk@gmail.com"
    def title = "MySQL Connector/J"
    def description = "MySQL Connector/J"
    def documentation = "http://grails.org/plugin/mysql-connectorj"
}
