

REM aopc15.bat classpath [-aoppath path_to_aop.xml [-aopclasspath path_to_annotated] [-report] [-verbose]  dir_or_file+
REM    classpath:        Classpath of your sourcefiles and all required libraries
REM    path_to_.aop.xml: Path to your *-aop.xml files. Use colon as separator  if you have more than one
REM    path_to_annotated Path to jars/directories that have annotated aspects. Use colon as separator if you have more than one.
REM    dir_or_file:      Directory containing files to be aop precompiled
REM    -verbose:         Specify if you want verbose output
REM    -report:          If specified, classes do not get instrumented. Instead you get an xml file containing the bindings applied.


REM Setup Environment Variables
SET AOP_HOME=Z:\WORKSPACE\Tools\JBossAOP
SET JAVA_HOME=Z:\WORKSPACE\Tools\JDK15
SET AOPPATH=-Djboss.aop.path=Z:\WORKSPACE\JBossAOP\HelloJBossAOP\jboss-aop.xml
SET AOPCLASSPATH=-Djboss.aop.class.path=Z:\WORKSPACE\JBossAOP\HelloJBossAOP\src

REM User CLass Path
SET USER_CLASSPATH=Z:\WORKSPACE\JBossAOP\HelloJBossAOP\src

REM Library Class Path
SET LIB_CLASSPATH=Z:\WORKSPACE\JBossAOP\HelloJBossAOP\lib\ojdbc14.jar
SET LIB_CLASSPATH=%LIB_CLASSPATH%;Z:\WORKSPACE\JBossAOP\HelloJBossAOP\lib\xerces.jar
SET LIB_CLASSPATH=%LIB_CLASSPATH%;Z:\WORKSPACE\JBossAOP\HelloJBossAOP\lib\xmlrpc.jar
SET LIB_CLASSPATH=%LIB_CLASSPATH%;Z:\WORKSPACE\JBossAOP\HelloJBossAOP\lib\xalan.jar

REM Setup AOP classpath
SET AOPC_CLASSPATH=%AOP_HOME%\lib-50\concurrent.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%AOP_HOME%\lib-50\javassist.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%AOP_HOME%\lib-50\jboss-aop-jdk50.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%AOP_HOME%\lib-50\jboss-aspect-library.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%AOP_HOME%\lib-50\jboss-common.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%AOP_HOME%\lib-50\qdox.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%AOP_HOME%\lib-50\trove.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%USER_CLASSPATH%;%LIB_CLASSPATH%


REM COMPILE
%JAVA_HOME%\bin\javac -classpath %LIB_CLASSPATH%;Z:\WORKSPACE\Tools\JBossAOP\lib-50\jboss-aop-jdk50.jar Z:\WORKSPACE\JBossAOP\HelloJBossAOP\src\*.java
REM %JAVA_HOME%\bin\java -classpath %AOPC_CLASSPATH% %AOPPATH% %AOPCLASSPATH% org.jboss.aop.standalone.Compiler -verbose Z:\WORKSPACE\JBossAOP\HelloJBossAOP\src\HelloAOP.class


