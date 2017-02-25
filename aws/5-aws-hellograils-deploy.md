### Install SDK MAN

	Follow Instructions Here:  http://sdkman.io/
	
### Install Groovy & Grails Locally

	Note: assuming you already have Java JDK 7 or 8 Installed
	
	sdk ls groovy
    sdk ls grails
    sdk install groovy 2.4.7
    sdk install grails 3.2.5
    sdk current

    grails --version

    | Grails Version: 3.2.5
    | Groovy Version: 2.4.7
    | JVM Version: 1.8.0_112


### Config Grails Database Connection for Production

	Update your grails-app/conf/application.yml File
	to Connector to your AWS MySQL DB.  
	
	Note: Sample Config Files are in the "ignore" folder.
	
### Generate and Deploy Application WAR file

	In your Grails Project Root Folder, Run Command:
	
		grails war
		
	Deploy Generated WAR file in:
	
		build/libs (folder)
		
	Note: To Deploy, SCP War file to EC2 Instance and Copy into Tomcat's  "webapp" folder.  
	
	This should be in the folder:
	
		/usr/share/apache-tomcat-7.0.75/webapps
		
	
				
				