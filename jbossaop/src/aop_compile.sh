java -classpath .:./jboss-aop-single.jar -Djboss.aop.path=./jboss-aop.xml -Djboss.aop.class.path=./:./jboss-aop-single.jar org.jboss.aop.standalone.Compiler -verbose $1 
