#!/bin/sh


usage(){
   echo Script file for running loadtime instrumented aop applications with hot swap enabled
   echo Usage:
   echo run-loadHotSwap.sh classpath [-aoppath path_to_aop.xml] [-aopclasspath path_to_annotated] [-verbose] Main.class [args]
   echo 
   echo    classpath:         Classpath of your source files and all required libraries
   echo 
   echo    path_to_.aop.xml:  Path to your *-aop.xml files (separated by colon)
   echo 
   echo    path_to_annotated: Path to jars/directories that have annotated aspects (separated by colon)
   echo
   echo    -verbose:          Specify if you want verbose output
   echo 
   echo    Main.class:        Your main class
   echo
   echo    args:              The args to Main.class
   exit 1
}

#Make sure have $1, $2, $3, and $4
if [ "x$1" = "x" ]; then
   usage
fi
if [ "x$2" = "x" ]; then
   usage
fi
if [ "x$3" = "x" ]; then
   usage
fi
if [ "x$4" = "x" ]; then
   usage
fi
if [ "$2" = "$4" ]; then
   usage
fi

USER_CLASSPATH=$1

AOPPATH=
AOPCLASSPATH=

if [ "$2" = "-aoppath" ]; then
   AOPPATH=-Djboss.aop.path=$3
   FILESTART=3
fi

if [ "$4" = "-aoppath" ]; then
   if [ "x$5" = "x" ]; then
      usage
   fi 
   AOPPATH=-Djboss.aop.path=$5 
   FILESTART=5
fi

if [ "$2" = "-aopclasspath" ]; then
   AOPCLASSPATH=-Djboss.aop.class.path=$3
   FILESTART=3
fi



if [ "$4" = "-aopclasspath" ]; then
   if [ "x$5" = "x" ]; then
      usage
   fi
   AOPCLASSPATH=-Djboss.aop.class.path=$5
   FILESTART=5
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
AOPC_CLASSPATH=$AOPC_CLASSPATH:$USER_CLASSPATH


CTR=0

for param in $*; do
   
   CTR=`expr $CTR + 1`
   if [ $CTR -gt $FILESTART ]; then
      MAINCLASS_AND_ARGS=$MAINCLASS_AND_ARGS" "$param
   fi
done


#Check for cygwin and convert path if necessary
if (cygpath --version) >/dev/null 2>/dev/null; then
   AOPC_CLASSPATH=`cygpath --path --windows $AOPC_CLASSPATH`
fi


java -javaagent:$SCRIPT_DIR/../lib/jboss-aop.jar=-hotSwap -classpath $AOPC_CLASSPATH $AOPPATH $AOPCLASSPATH $MAINCLASS_AND_ARGS

