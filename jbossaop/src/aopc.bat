@echo off

set LIB_DIR=%~dp0..\lib\

IF %1a==a goto display_usage
IF %2a==a goto display_usage
IF %3a==a goto display_usage
IF %4a==a goto display_usage
IF %2==%4 goto display_usage

SET USER_CLASSPATH=%1
SET AOPPATH=
SET AOPCLASSPATH=

IF %2==-aoppath SET AOPPATH=-Djboss.aop.path=%3
IF %2==-aopclasspath SET AOPCLASSPATH=-Djboss.aop.class.path=%3

IF %4%5==-aoppath goto display_usage
IF %4%5==-aopclasspath goto display_usage
IF %4==-aoppath SET AOPPATH=-Djboss.aop.path=%5 
IF %4==-aopclasspath SET AOPCLASSPATH=-Djboss.aop.class.path=%5



IF %4==-aoppath shift 
IF %3==-aoppath shift 
IF %4==-aopclasspath shift 
IF %3==-aopclasspath shift 


shift
shift
shift
set ARGS_AND_FILES=
REM get all the command line args
:setupArgs
if %1a==a goto doneStart
	set ARGS_AND_FILES=%ARGS_AND_FILES% %1
shift
goto setupArgs

:doneStart


REM Setup AOP classpath
SET AOPC_CLASSPATH=%LIB_DIR%javassist.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-aop-client.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-reflect.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-mdr.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-logging-log4j.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-logging-spi.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-standalone-aspect-library.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%log4j.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%pluggable-instrumentor.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-aop.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-aspect-library.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%jboss-common-core.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%LIB_DIR%trove.jar
SET AOPC_CLASSPATH=%AOPC_CLASSPATH%;%USER_CLASSPATH%


java -classpath %AOPC_CLASSPATH% %AOPPATH% %AOPCLASSPATH% org.jboss.aop.standalone.Compiler %ARGS_AND_FILES%

goto end

:display_usage
echo Batch file for running the aop precopiler
echo Usage:
echo aopc.bat classpath [-aoppath path_to_aop.xml] [-aopclasspath path_to_annotated] [-report] [-verbose]  dir_or_file+
   echo    classpath:         Classpath of your sourcefiles and all required libraries
   echo    path_to_.aop.xml:  Path to your *-aop.xml files (separated by colon)
   echo    path_to_annotated: Path to jars/directories that have annotated aspects (separated by colon)
   echo    dir_or_file:       Directory containing files to be aop precompiled
   echo    -verbose:          Specify if you want verbose output
   echo    -report:           If specified, classes do not get instrumented. Instead you get an xml file containing the bindings applied

:end

