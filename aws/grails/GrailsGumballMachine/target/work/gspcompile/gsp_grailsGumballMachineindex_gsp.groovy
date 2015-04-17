import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_grailsGumballMachineindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',4,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',5,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',5,[:],2)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',82,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
invokeTag('message','g',84,['code':("default.link.skip.label"),'default':("Skip to content&hellip;")],-1)
printHtmlPart(6)
invokeTag('meta','g',88,['name':("app.version")],-1)
printHtmlPart(7)
invokeTag('meta','g',89,['name':("app.grails.version")],-1)
printHtmlPart(8)
expressionOut.print(GroovySystem.getVersion())
printHtmlPart(9)
expressionOut.print(System.getProperty('java.version'))
printHtmlPart(10)
expressionOut.print(grails.util.Environment.reloadingAgentEnabled)
printHtmlPart(11)
expressionOut.print(grailsApplication.controllerClasses.size())
printHtmlPart(12)
expressionOut.print(grailsApplication.domainClasses.size())
printHtmlPart(13)
expressionOut.print(grailsApplication.serviceClasses.size())
printHtmlPart(14)
expressionOut.print(grailsApplication.tagLibClasses.size())
printHtmlPart(15)
for( plugin in (applicationContext.getBean('pluginManager').allPlugins) ) {
printHtmlPart(16)
expressionOut.print(plugin.name)
printHtmlPart(17)
expressionOut.print(plugin.version)
printHtmlPart(18)
}
printHtmlPart(19)
for( c in (grailsApplication.controllerClasses.sort { it.fullName }) ) {
printHtmlPart(20)
createTagBody(3, {->
expressionOut.print(c.fullName)
})
invokeTag('link','g',116,['controller':(c.logicalPropertyName)],3)
printHtmlPart(21)
}
printHtmlPart(22)
})
invokeTag('captureBody','sitemesh',121,[:],1)
printHtmlPart(23)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1406746854000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}

@org.codehaus.groovy.grails.web.transform.LineNumber(
	lines = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 82, 82, 83, 84, 84, 84, 84, 88, 88, 89, 89, 90, 90, 91, 91, 92, 92, 93, 93, 94, 94, 95, 95, 96, 96, 100, 100, 101, 101, 101, 101, 102, 102, 115, 115, 116, 116, 116, 116, 116, 117, 117, 121, 121, 121, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
	sourceName = "index.gsp"
)
class ___LineNumberPlaceholder { }
