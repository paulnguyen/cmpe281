all: compile war deploy

compile: 
	javac -cp /home/ubuntu/workspace/xmlrpc/cloud9/lib/servlet-api.jar:/home/ubuntu/workspace/xmlrpc/cloud9/lib/xalan.jar:/home/ubuntu/workspace/xmlrpc/cloud9/lib/xerces.jar -d /home/ubuntu/workspace/xmlrpc/cloud9/build/WEB-INF/classes /home/ubuntu/workspace/xmlrpc/cloud9/source/edu/sjsu/cmpe281/*.java /home/ubuntu/workspace/xmlrpc/cloud9/source/helma/xmlrpc/*.java
	
war:
	/home/ubuntu/workspace/xmlrpc/cloud9/genwar.sh
	
deploy:
	rm -rf /home/ubuntu/apache-tomcat/webapps/xmlrpc
	rm -rf /home/ubuntu/apache-tomcat/webapps/xmlrpc.war
	cp /home/ubuntu/workspace/xmlrpc/cloud9/xmlrpc.war /home/ubuntu/apache-tomcat/webapps


