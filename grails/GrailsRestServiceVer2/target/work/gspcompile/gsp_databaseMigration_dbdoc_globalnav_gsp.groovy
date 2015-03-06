import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

@GrailsPlugin(name='database-migration', version='1.4.0')
class gsp_databaseMigration_dbdoc_globalnav_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/plugins/database-migration-1.4.0/grails-app/views/dbdoc/_globalnav.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
createTagBody(2, {->
createClosureForHtmlPart(2, 3)
invokeTag('captureTitle','sitemesh',4,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',4,[:],2)
printHtmlPart(3)
expressionOut.print(createLink(action: 'dbdoc_stylesheet_css'))
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',6,[:],1)
printHtmlPart(5)
createTagBody(1, {->
printHtmlPart(6)
expressionOut.print(createLink(action: 'currenttables'))
printHtmlPart(7)
expressionOut.print(createLink(action: 'authors'))
printHtmlPart(8)
expressionOut.print(createLink(action: 'changelogs'))
printHtmlPart(9)
expressionOut.print(createLink(action: 'pending'))
printHtmlPart(10)
expressionOut.print(createLink(action: 'pendingsql'))
printHtmlPart(11)
expressionOut.print(createLink(action: 'recent'))
printHtmlPart(12)
})
invokeTag('captureBody','sitemesh',23,['bgcolor':("white")],1)
printHtmlPart(13)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1365785436000L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
