NOTE: from the 2.0 release of JBoss AOP only JDK1.5 is supported. This means that that both JBoss AS and AOP
need to be compiled with JDK1.5 before you upgrade JBoss AOP.

To upgrade your jboss instance:

* If installing in a version before JBoss 4.0.4, you should leave javassist.jar in place in this folder.

*If installing in jboss 4.0.4 or later, the classes contained in javassist.jar will already be available,
and you should do the following to avoid versioning conflicts:
-move javassist.jar to ../../lib/javassist.jar

*On JBoss 4.0.4 or later, and if using JDK 1.4, you should upgrade the version of jbossretro-rt.jar by moving it 
from the jboss-aop-jdk14.deployer to ../../lib/jbossretro-rt.jar

* Note that this will not replace the jboss-aspect-library(-jdk50).jar, which is tied to application
server version. If you are upgrading from JBoss AOP 1.5.x, you should remove the following packages and classes
contained therein from your jboss-aspect-library(-jdk50).jar:
-org.jboss.aop
-org.jboss.aop.deployment

*Also, in the existing jboss-aspect-library(-jdk50).jar you should delete the classes that exist in 
jboss-standalone-aspect-library-jdkxx.jar

* If you are deploying the jdk 1.4 deployer and upgrading from JDK 1.5, we need to modify the interfaces 
specifying the simulated annotations in jboss-aspect-library.jar. The build.xml file does this 
as part of the install step.

Alternatively, you can use the ant script, which will automate all these tasks for you:
1) Edit jboss.properties file, setting the root of the jboss installation
and the server configuration you wish to patch.
2) Run the command:
$ ant 