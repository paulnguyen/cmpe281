To upgrade your jboss instance:
1) Copy the contents of the lib folder into the JBoss-5.x/lib/ folder
2) Copy the jboss-aop-jdk50.deployer over the JBoss-5.x/server/xxx/deployers/jboss-aop-jboss5.deployer/ folder
3) Copy the jboss-aop-client.jar (located at ../lib) into the JBoss-5.x/client/ folder.
Note that this will not replace the jboss-aspect-library.jar, which is tied to application
server version
Alternatively, you can use the ant script:
1) Edit jboss.properties file, setting the root of the jboss installation
and the server configuration you wish to patch.
2) Run the command:
$ ant 