<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:template match="/">

<!--
  This is a simple stylesheet, derived from XDoclet's xtags2xdoc.xslt,
  that transforms the standard xtags.xml tag documentation file into an
  html file suitable for the JBoss website.
-->

<html>
<head>
<!--
  If you want to preview this page in a browser, independently of the JBoss
  website, grab a copy of themes/impact/style/style.css and put it in the
  same directory as this generated html file.
-->

<link rel="stylesheet" href="style.css"/>

</head>
<body>

<xsl:comment> =============================== CUT HERE ============================== </xsl:comment>

  <!-- introduction docs, this lot should probably go in a separate file
       included in the stylesheet somehow, maybe another time :) -->
  <h2>JBoss AOP XDoclet Support</h2>
  <h3>Contents</h3>
  
  <dl>
    <dt><a href="#introduction">Introduction</a></dt>
    <dd></dd>
    <dt><a href="#ant">Using with Ant</a></dt>
    <dd>
      <dl>
        <dt><a href="#antmerge">Merge Points</a></dt>
        <dd></dd>
      </dl>
    </dd>
    <dt><a href="#xml">XmlLoadable and arbitrary XML data</a></dt>
    <dd>
      <dl>
        <dt><a href="#xmlattribute">The xml attribute</a></dt>
        <dd></dd>
        <dt><a href="#transforming">Transforming attributes to XML elements</a></dt>
        <dd></dd>
      </dl>
    </dd>
    <dt><a href="#tagorder">Tag Ordering</a></dt>
    <dd></dd>
    <dt><a href="#tagdocs">Tag Documentation</a></dt>
    <dd></dd>
  </dl>
  
  <h3><a name="introduction">Introduction</a></h3>

  <p align="left">
  An XDoclet jar is included with JBoss 4,
  <code>tools/xdoclet/xdoclet-modules-jboss-aop.jar</code>, allowing you to
  write your JBoss AOP deployment descriptors using tags in your source files,
  in the same way as for EJBs, JMX MBeans, etc.  Currently, most of the AOP
  elements are supported and more will be added shortly.
  </p>
  
  <h3><a name="ant">Using with Ant</a></h3>
  
  <p align="left">
  Using JBoss AOP XDoclet in your Ant build.xml file is the same as using any
  custom XDoclet task. To access the JBoss AOP XDoclet tools you can either
  copy the jar, <code>xdoclet-modules-jboss-aop.jar</code>, into your XDoclet
  lib directory, or just add it to the classpath when you define the task.
  To define the AOP task, use a &lt;taskdef...&gt; element, include your java
  source files using a nested &lt;fileset&gt; element, and call the jbossaop
  subtask to generate the jboss-aop.xml deployment descriptor, for example:
  <pre>
    &lt;taskdef name="aopdoclet"
             classname="xdoclet.modules.jboss.aop.JBossAopTask"
             classpathref="xdoclet.classpath"/&gt;

    &lt;aopdoclet destdir="${build.dir}/META-INF"&gt;
      &lt;fileset dir="${src.dir}"&gt;
        &lt;include name="**/*.java"/&gt; 
      &lt;/fileset&gt;
      &lt;jbossaop/&gt;
    &lt;/aopdoclet&gt;
  </pre>
  </p>

  <p align="left">
  The task class, <code>xdoclet.modules.jboss.aop.JBossAopTask</code>, is
  derived from <code>xdoclet.DocletTask</code> and consequently supports
  all the attributes and nested elements of DocletTask, for example
  destdir, force, mergeDir, etc.
  </p>
  
  <h4><a name="antmerge">Merge Points</a></h4>
  
  <p align="left">
  Merge points are provided at the global level and for each individual
  source file processed. For global level AOP XML data, simply supply
  a file called jboss-aop.xml in the top of the merge directory, the
  contents of this file will be merged into the generated jboss-aop.xml
  file.  For individual class level AOP XML data, supply a file called
  classname-aop.xml in a directory underneath the merge directory that
  mirrors the package directory structure of the source file.
  </p>  

  <h3><a name="xml">XmlLoadable and arbitrary XML</a></h3>

  <p align="left">
  An Interceptor class, or an Interceptor factory class, may implement the
  <code>org.jboss.util.xml.XmlLoadable</code> interface, which allows arbitrary
  configuration data, in XML format, to be passed to the class at construction.
  In addition, custom class metadata potentially requires arbitrary XML data
  included in the jboss-aop.xml file, to specify metadata at class, method level.
  There are two ways to specify this data in the XDoclet tags:
  </p>

  <ol>
    <li>The xml attribute</li>
    <li>Transforming attributes to XML elements</li>
  </ol>
  
  <h4><a name="xmlattribute">The xml attribute</a></h4>

  <p align="left">
  You can simply write the required XML configuration data in the xml attribute.
  For example, the following metadata declaration:
  </p>
  
  <pre>
    package com.acme;
    /**
     * @jboss-aop.metadata group="MyGroup" 
     *                     xml="&lt;config&gt;some value&lt;/config&gt;"
     */
    public class MyClass
    {
    }
  </pre>

  <p align="left">
  results in:
  </p>
  
  <pre>
    &lt;class-metadata class="com.acme.MyClass" group="MyGroup"&gt;
      &lt;default&gt;
        &lt;config&gt;some value&lt;/config&gt;
      &lt;/default&gt;
    &lt;/class-metadata&gt;
  </pre>

  <h4><a name="transforming">Transforming attributes to XML elements</a></h4>

  <p align="left">
  If the xml attribute is not present, any unrecognised attributes of the
  tag will be converted into XML elements, using the attribute name
  as the element name and the attribute value as the element content.  So the
  previous example could be written as:
  </p>

  <pre>
    package com.acme;
    /**
     * @jboss-aop.metadata group="MyGroup" 
     *                     config="some value"
     */
    public class MyClass
    {
    }
  </pre>
  
  <h3><a name="tagorder">Tag Ordering</a></h3>
  
  <p align="left">
  Stack and Pointcut definitions are just lists of Interceptors and,
  optionally, other Stacks.  The ordering of such a list is obviously important.
  To support declaring these elements in a source file, multiple XDoclet tags
  may be used, the ordering of the tags in the source file is maintained in
  the generated XML data.  For example, a Stack may be declared as follows:
  </p>
  
  <pre>
    /**
     * @jboss-aop.stack name="MyFirstStack"
     * @jboss-aop.stack class="com.foo.SimpleInterceptor"
     * @jboss-aop.stack factory="com.foo.AnotherInterceptorFactory" 
     * @jboss-aop.stack class="com.foo.TestingInterceptor"
     *                  singleton="true"
     *                  config="Some config data"
     */
  </pre>

  <p align="left">
  resulting in the following generated XML:
  </p>
  
  <pre>
    &lt;stack name="MyFirstStack"&gt;
      &lt;interceptor class="com.foo.SimpleInterceptor"&gt;
      &lt;/interceptor&gt;
      &lt;interceptor factory="com.foo.AnotherInterceptorFactory"&gt;
      &lt;/interceptor&gt;
      &lt;interceptor class="com.foo.TestingInterceptor"
                   singleton="true"&gt;
        &lt;config&gt;Some config data&lt;/config&gt;
      &lt;/interceptor&gt;
    &lt;/stack&gt;
  </pre>

  <p align="left">
  And some example pointcut definitions:
  </p>
  
  <pre>
    package com.acme;
    
    /**
     * @jboss-aop.pointcut class="com.test.SomeInterceptor"
     *                     singleton="true"
     *                     config="random"
     * @jboss-aop.pointcut interceptor-ref="Another"
     * @jboss-aop.pointcut stack-ref="SomeStack"
     * @jboss-aop.pointcut factory="com.test.SomeInterceptorFactory"
     */
    public class MyClass
    {
      /**
       * @jboss-aop.pointcut interceptor-ref="SomeInterceptor"
       * @jboss-aop.pointcut class="com.foo.MethodInterceptor"
       */
      public void someMethod()
      {
      }
    }
  </pre>

  <p align="left">
  resulting in the following generated XML:
  </p>
  
  <pre>
    &lt;class-pointcut class="com.acme.MyClass"&gt;
      &lt;interceptors&gt;
        &lt;interceptor class="com.test.SomeInterceptor" singleton="true"&gt;
          &lt;config&gt;random&lt;/config&gt;
        &lt;/interceptor&gt;
        &lt;interceptor-ref name="Another"/&gt;
        &lt;stack-ref name="SomeStack"/&gt;
        &lt;interceptor factory="com.test.SomeInterceptorFactory"&gt;
        &lt;/interceptor&gt;
      &lt;/interceptors&gt;
    &lt;/class-pointcut&gt;
  
    &lt;method-pointcut class="com.acme.MyClass"
                     methodName="someMethod"&gt;
      &lt;interceptors&gt;
        &lt;interceptor-ref name="SomeInterceptor"/&gt;
        &lt;interceptor class="com.foo.MethodInterceptor"&gt;
        &lt;/interceptor&gt;
      &lt;/interceptors&gt;
    &lt;/method-pointcut&gt;
  </pre>
  
  <p align="left">
  This approach has the drawback that only a single Stack or Pointcut may be
  declared per class, method or field, as all tags are combined to generate a
  single element.
  </p>
  
  <h3><a name="tagdocs">Tag Documentation</a></h3>

  <p align="left">
  In the following documentation table, each tag is marked with the version
  of JBoss that supports it, more specifically:
  </p>
  
  <dl>
    <dt>DR2</dt>
    <dd>This tag is supported in the DR2 release</dd>
    <dt>CVS</dt>
    <dd>This tag is only available in the current CVS head version</dd>
  </dl>
  
  <xsl:for-each select="xdoclet/namespace">
    <table cellspacing="5" cellpadding="5">
      <tr>
        <td bgcolor="#336699">
          <font size="-2"
                color="#FFFFFF"
                face="verdana,arial,sans-serif">
            <b>Tag Reference for @<xsl:value-of select="name"/></b>
          </font>
        </td>
      </tr>
      <tr>
        <td style="border-style:solid;border-width:1px;border-color:#6D9AD3;">
            <p>Class Level Tags</p>
          <xsl:for-each select="tags/tag">
              <xsl:if test="contains(./level, 'class')">
                  <p><a>
                      <xsl:if test="contains(./unique, 'true')">
                          <xsl:attribute name="href"><xsl:text>#@</xsl:text><xsl:value-of select="name"/><xsl:text> (0..1)</xsl:text></xsl:attribute>
                      </xsl:if>
                      <xsl:if test="not(contains(./unique, 'true'))">
                          <xsl:attribute name="href"><xsl:text>#@</xsl:text><xsl:value-of select="name"/><xsl:text> (0..*)</xsl:text></xsl:attribute>
                      </xsl:if>
                      <xsl:attribute name="title"><xsl:value-of select="normalize-space(usage-description)"/></xsl:attribute>
                      <xsl:text>@</xsl:text><xsl:value-of select="name"/>
                  </a> &#160; <xsl:value-of select="version"/></p>
                  <xsl:text> </xsl:text>
              </xsl:if>
          </xsl:for-each>
        </td>
      </tr>
      <tr>
        <td style="border-style:solid;border-width:1px;border-color:#6D9AD3;">
          <p>Method Level Tags</p>
          <xsl:for-each select="tags/tag">
              <xsl:if test="contains(./level, 'method')">
                  <p><a>
                      <xsl:if test="contains(./unique, 'true')">
                          <xsl:attribute name="href"><xsl:text>#@</xsl:text><xsl:value-of select="name"/><xsl:text> (0..1)</xsl:text></xsl:attribute>
                      </xsl:if>
                      <xsl:if test="not(contains(./unique, 'true'))">
                          <xsl:attribute name="href"><xsl:text>#@</xsl:text><xsl:value-of select="name"/><xsl:text> (0..*)</xsl:text></xsl:attribute>
                      </xsl:if>
                      <xsl:attribute name="title"><xsl:value-of select="normalize-space(usage-description)"/></xsl:attribute>
                      <xsl:text>@</xsl:text><xsl:value-of select="name"/>
                  </a> &#160; <xsl:value-of select="version"/></p>
                  <xsl:text> </xsl:text>
              </xsl:if>
          </xsl:for-each>
        </td>
      </tr>
      <tr>
        <td bgcolor="#336699">
          <font size="-2"
                color="#FFFFFF"
                face="verdana,arial,sans-serif">
            <b>Tag Usage, Class Level (@<xsl:value-of select="name"/>)</b>
          </font>
        </td>
      </tr>
      <tr>
        <td>
            <xsl:value-of select="usage-description"/>
        </td>
      </tr>
      <xsl:for-each select="tags/tag">
        <xsl:if test="contains(./level, 'class')">
          <tr>
            <td bgcolor="#6D9AD3">
              <font size="-2"
                    color="#FFFFFF"
                    face="verdana,arial,sans-serif"><b>
              <xsl:if test="contains(./unique, 'true')">
                  <a name="@{name} (0..1)" class="name">@<xsl:value-of select="name"/> (0..1)</a>
              </xsl:if>
              <xsl:if test="not(contains(./unique, 'true'))">
                  <a name="@{name} (0..*)" class="name">@<xsl:value-of select="name"/> (0..*)</a>
              </xsl:if>
               &#160; <xsl:value-of select="version"/>
              </b></font>
            </td>
          </tr>
          <tr>
            <td>
                <xsl:value-of select="usage-description"/>
            </td>
          </tr>
          <tr>
            <td>
              <xsl:if test="count(parameter) &gt; 0">
                <table width="100%">
                  <tr>
                    <th bgcolor="#DDDDDD">Parameter</th>
                    <th bgcolor="#DDDDDD">Type</th>
                    <th bgcolor="#DDDDDD">Applicability</th>
                    <th bgcolor="#DDDDDD">Description</th>
                    <th bgcolor="#DDDDDD">Mandatory</th>
                  </tr>
                  <xsl:for-each select="parameter">
                    <tr valign="top">
                      <td bgcolor="#EEEEEE"><xsl:value-of select="name"/></td>
                      <td bgcolor="#EEEEEE"><xsl:value-of select="@type"/></td>
                      <td bgcolor="#EEEEEE"><xsl:value-of select="condition-description"/></td>
                      <td bgcolor="#EEEEEE"><xsl:value-of select="usage-description"/>
                        <xsl:if test="count(option-sets) &gt;0" >
                          <xsl:call-template name="process-param-options">
                              <xsl:with-param name="node" select="option-sets"/>
                          </xsl:call-template>
                        </xsl:if>
                      </td>
                      <td bgcolor="#EEEEEE">
                        <xsl:if test="contains(./mandatory, 'true')">
                          <b><xsl:value-of select="mandatory"/></b>
                        </xsl:if>
                        <xsl:if test="not(contains(./mandatory, 'true'))">
                          <xsl:value-of select="mandatory"/>
                        </xsl:if>
                      </td>
                    </tr>
                  </xsl:for-each>
                </table>
              </xsl:if>
            </td>
          </tr>
        </xsl:if>
      </xsl:for-each>
      <tr>
        <td bgcolor="#336699">
          <font size="-2"
                color="#FFFFFF"
                face="verdana,arial,sans-serif">
            <b>Tag Usage, Method Level (@<xsl:value-of select="name"/>)</b>
          </font>
        </td>
      </tr>
      <tr>
        <td><xsl:value-of select="usage-description"/></td>
      </tr>
      <xsl:for-each select="tags/tag">
        <xsl:if test="contains(./level, 'method')">
          <tr>
            <td bgcolor="#6D9AD3">
              <font size="-2"
                    color="#FFFFFF"
                    face="verdana,arial,sans-serif"><b>
              <xsl:if test="contains(./unique, 'true')">
                  <a name="@{name} (0..1)" class="name">@<xsl:value-of select="name"/> (0..1)</a>
              </xsl:if>
              <xsl:if test="not(contains(./unique, 'true'))">
                  <a name="@{name} (0..*)" class="name">@<xsl:value-of select="name"/> (0..*)</a>
              </xsl:if>
               &#160; <xsl:value-of select="version"/>
              </b></font>
            </td>
          </tr>
          <tr>
            <td>
              <xsl:value-of select="usage-description"/>
            </td>
          </tr>
          <tr>
            <td>
              <xsl:if test="count(parameter) &gt; 0">
                <table width="100%">
                  <tr>
                    <th bgcolor="#DDDDDD">Parameter</th>
                    <th bgcolor="#DDDDDD">Type</th>
                    <th bgcolor="#DDDDDD">Applicability</th>
                    <th bgcolor="#DDDDDD">Description</th>
                    <th bgcolor="#DDDDDD">Mandatory</th>
                  </tr>
                  <xsl:for-each select="parameter">
                    <tr valign="top">
                      <td bgcolor="#EEEEEE"><xsl:value-of select="name"/></td>
                      <td bgcolor="#EEEEEE"><xsl:value-of select="@type"/></td>
                      <td bgcolor="#EEEEEE"><xsl:value-of select="condition-description"/></td>
                      <td bgcolor="#EEEEEE"><xsl:value-of select="usage-description"/>
                        <xsl:if test="count(option-sets) &gt;0" >
                          <xsl:call-template name="process-param-options">
                            <xsl:with-param name="node" select="option-sets"/>
                          </xsl:call-template>
                        </xsl:if>
                      </td>
                      <td bgcolor="#EEEEEE">
                        <xsl:if test="contains(./mandatory, 'true')">
                          <b><xsl:value-of select="mandatory"/></b>
                        </xsl:if>
                        <xsl:if test="not(contains(./mandatory, 'true'))">
                          <xsl:value-of select="mandatory"/>
                        </xsl:if>
                      </td>
                    </tr>
                  </xsl:for-each>
                </table>
              </xsl:if>
            </td>
          </tr>
        </xsl:if>
      </xsl:for-each>
    </table>

<xsl:comment> =============================== CUT HERE ============================== </xsl:comment>
    
  </xsl:for-each>
</body>
</html>
</xsl:template>

<!-- this template describes the options that are valid for a parameter -->
<xsl:template name="process-param-options">
    <xsl:param name="node"/>
      <br/>Valid options are:
      <ul>
      <xsl:for-each select="$node/option-set/options/option">
              <li><xsl:value-of select="."/></li>
      </xsl:for-each>
      </ul>                                            
</xsl:template>
</xsl:stylesheet>

