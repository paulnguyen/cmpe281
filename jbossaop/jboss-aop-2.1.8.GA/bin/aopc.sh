#!/bin/sh


usage(){
   echo Batch file for running the aop precompiler
   echo Usage:
   echo aopc.sh [-aoppath path_to_aop.xml] [-aopclasspath path_to_annotated] [-report] [-verbose] dir_or_file+
   echo 
   echo    classpath:         Classpath of your sourcefiles and all required libraries
   echo 
   echo    path_to_.aop.xml:  Path to your *-aop.xml files (separated by colon)
   echo 
   echo    path_to_annotated: Path to jars/directories that have annotated aspects (separated by colon)
   echo 
   echo    dir_or_file:       Directory containing files to be aop precompiled
   echo 
   echo    -verbose:          Specify if you want verbose output
   echo 
   echo    -report:           If specified, classes do not get instrumented. Instead you get an xml file containing the bindings applied
   
   exit 1
}

#Make sure have $1, $2 and $3
if [ "x$1" = "x" ]; then
   usage
fi
if [ "x$2" = "x" ]; then
   usage
fi
if [ "x$3" = "x" ]; then
   usage
fi
if [ "$1" = "$3" ]; then
   usage
fi

AOPPATH=
AOPCLASSPATH=

if [ "$1" = "-aoppath" ]; then
   AOPPATH=-Djboss.aop.path=$2
   FILESTART=2
fi

if [ "$3" = "-aoppath" ]; then
   if [ "x$4" = "x" ]; then
      usage
   fi 
   AOPPATH=-Djboss.aop.path=$4 
   FILESTART=4
fi

if [ "$1" = "-aopclasspath" ]; then
   AOPCLASSPATH=-Djboss.aop.class.path=$2
   FILESTART=2
fi



if [ "$3" = "-aopclasspath" ]; then
   if [ "x$4" = "x" ]; then
      usage
   fi
   AOPCLASSPATH=-Djboss.aop.class.path=$4
   FILESTART=4
fi



SCRIPT_DIR=$(dirname $(which $0));

AOPC_CLASSPATH=$SCRIPT_DIR/../lib/javassist.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/jboss-aop-client.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/jboss-reflect.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/jboss-mdr.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/jboss-logging-log4j.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/jboss-logging-spi.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/jboss-standalone-aspect-library.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/log4j.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/pluggable-instrumentor.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/jboss-aop.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/jboss-common-core.jar
AOPC_CLASSPATH=$AOPC_CLASSPATH:$SCRIPT_DIR/../lib/trove.jar


CTR=0

for param in $*; do
   CTR=`expr $CTR + 1`
   if [ $CTR -gt $FILESTART ]; then
      ARGS_AND_FILES=$ARGS_AND_FILES" "$param
      AOPC_CLASSPATH=$AOPC_CLASSPATH:$param
   fi
done

#Check for cygwin and convert path if necessary
if (cygpath --version) >/dev/null 2>/dev/null; then
   AOPC_CLASSPATH=`cygpath --path --windows $AOPC_CLASSPATH`
fi

java -classpath $AOPC_CLASSPATH $AOPPATH $AOPCLASSPATH org.jboss.aop.standalone.Compiler $ARGS_AND_FILES

