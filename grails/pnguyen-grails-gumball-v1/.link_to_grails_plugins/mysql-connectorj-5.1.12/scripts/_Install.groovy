if(!confirmInput("Overwrite existing DataSource.groovy?", "overwrite.datasource")) {
    return
}

templateFile = "${pluginBasedir}/src/templates/DataSource.groovy"
datasourceFile = "${basedir}/grails-app/conf/DataSource.groovy"
ant.copy(file: templateFile, tofile: datasourceFile, overwrite: true)
ant.replace(file: datasourceFile, token: "@app.name@", value: grailsAppName)


