Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

// Hard coded for installation purpose
def jQueryVersion = '1.7.1'
def jQuerySources = 'jquery'

includeTargets << grailsScript("_GrailsEvents")

target ('default': "Sets the current application version") {
    Ant.sequential {
        event("StatusUpdate", ["Downloading JQuery ${jQueryVersion}"])

        def files = ["jquery-${jQueryVersion}.js", "jquery-${jQueryVersion}.min.js"]

        mkdir(dir:"${basedir}/web-app/js/${jQuerySources}")
        files.each {
            get(dest:"${basedir}/web-app/js/${jQuerySources}/${it}",
                src:"http://code.jquery.com/${it}",
                verbose:true)
        }
    }
    event("StatusFinal", ["JQuery ${jQueryVersion} installed successfully"])
}


