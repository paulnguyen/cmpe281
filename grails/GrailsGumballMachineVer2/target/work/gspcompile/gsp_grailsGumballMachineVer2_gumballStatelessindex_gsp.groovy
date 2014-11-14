import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_grailsGumballMachineVer2_gumballStatelessindex_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/gumballStateless/index.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',7,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(5)
expressionOut.print(flash.state)
printHtmlPart(6)
expressionOut.print(flash.model)
printHtmlPart(7)
expressionOut.print(flash.serial)
printHtmlPart(8)
expressionOut.print(flash.message)
printHtmlPart(9)
expressionOut.print(createLinkTo(dir: 'images', file: 'giant-gumball-machine.jpg'))
printHtmlPart(10)
})
invokeTag('captureBody','sitemesh',35,[:],1)
printHtmlPart(11)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1415341369000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
