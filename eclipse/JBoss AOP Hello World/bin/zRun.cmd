
REM Setup Environment Variables
SET AOP_HOME=Z:\WORKSPACE\Tools\JBossAOP
SET JAVA_HOME=Z:\WORKSPACE\Tools\JDK15
SET AOPPATH=-Djboss.aop.path=Z:\WORKSPACE\JBossAOP\HelloJBossAOP\jboss-aop.xml

REM User Class Path
SET USER_CLASSPATH=Z:\WORKSPACE\JBossAOP\HelloJBossAOP\src

REM Library Class Path
SET LIB_CLASSPATH=Z:\WORKSPACE\JBossAOP\HelloJBossAOP\lib\ojdbc14.jar
SET LIB_CLASSPATH=%LIB_CLASSPATH%;Z:\WORKSPACE\JBossAOP\HelloJBossAOP\lib\xerces.jar
SET LIB_CLASSPATH=%LIB_CLASSPATH%;Z:\WORKSPACE\JBossAOP\HelloJBossAOP\lib\xmlrpc.jar
SET LIB_CLASSPATH=%LIB_CLASSPATH%;Z:\WORKSPACE\JBossAOP\HelloJBossAOP\lib\xalan.jar

REM Setup AOP classpath
SET CLASSPATH=%AOP_HOME%\lib-50\concurrent.jar
SET CLASSPATH=%CLASSPATH%;%AOP_HOME%\lib-50\javassist.jar
SET CLASSPATH=%CLASSPATH%;%AOP_HOME%\lib-50\jboss-aop-jdk50.jar
SET CLASSPATH=%CLASSPATH%;%AOP_HOME%\lib-50\jboss-common.jar
SET CLASSPATH=%CLASSPATH%;%AOP_HOME%\lib-50\trove.jar
SET CLASSPATH=%CLASSPATH%;%USER_CLASSPATH%;%LIB_CLASSPATH

REM AOP RUN
REM %JAVA_HOME%\bin\java -classpath %CLASSPATH% %AOPPATH% HelloAOP
REM %JAVA_HOME%\bin\java -classpath %CLASSPATH% %AOPPATH% HotelCustomerScreen

REM Standard Java 5 Run
%JAVA_HOME%\bin\java -classpath %CLASSPATH% HotelCustomerScreen



