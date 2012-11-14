#!/bin/sh

AOP_PATH=$PWD/../lib

CLASSPATH=$AOP_PATH/javassist.jar
CLASSPATH=$CLASSPATH:$AOP_PATH/jboss-reflect.jar
CLASSPATH=$CLASSPATH:$AOP_PATH/jboss-mdr.jar
CLASSPATH=$CLASSPATH:$AOP_PATH/jboss-logging-spi.jar
CLASSPATH=$CLASSPATH:$AOP_PATH/jboss-common-core.jar
CLASSPATH=$CLASSPATH:$AOP_PATH/trove.jar
CLASSPATH=$CLASSPATH:$AOP_PATH/jboss-aop.jar
CLASSPATH=$CLASSPATH:$PWD/lib/aophelper.jar


java -javaagent:$PWD/../lib/jboss-aop.jar -cp $CLASSPATH -Djboss.aop.path=resources/jboss-aop.xml  org.jboss.aophelper.ui.AopHelperFrame
