<%@page language="java" contentType="text/html" import="org.jboss.injbossaop.lib.ExampleValue"%>
<jsp:useBean id="exampleValue" scope="session" class="org.jboss.injbossaop.lib.ExampleValue" />
<html>
<head><title>AOP in JBoss Simple WAR example</title></head>
<body>
<H1>Hello</H1>
<jsp:getProperty name="exampleValue" property="message"/>
<form action="srv">
<input type="text" name="field1">
<input type="submit">
</form>
</body>
</html>