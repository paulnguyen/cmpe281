
## Java JDK (CentOS)

 ​	sudo yum install java-1.8.0-openjdk-devel 

 	** Select Java 8 Option for: **

    sudo /usr/sbin/alternatives --config java
    sudo /usr/sbin/alternatives --config javac
    
    NOTE: JAVA_HOME = /usr/lib/jvm/java-1.8.0-openjdk.x86_64


## Java JDK (Ubuntu)

    apt-get update
    apt-get install default-jre
    apt-get install default-jdk
    apt-get install vim
    apt-get install zip
    apt-get install unzip


## Grails / SDK

- Note: assuming you already have Java JDK 7 or 8 Installed

	curl -s "https://get.sdkman.io" | bash
	source "/root/.sdkman/bin/sdkman-init.sh"
	sdk version

    sdk ls grails
    sdk install grails 4.0.0
    sdk current

    grails --version

	| Grails Version: 4.0.0
	| JVM Version: 1.8.0_181

	grails compile
	grails war
	grails run-app
	grails test run-app	


## MySQL Database

	mysql --user=user_name --password=your_password db_name
 
 	mysql --u=root --p	
 	password: ***** (enter your password)
 
	mysql> create database cmpe281 ;
	mysql> use cmpe281 ;
	mysql> show tables ;

	CREATE TABLE gumball (
	  id bigint(20) NOT NULL AUTO_INCREMENT,
	  version bigint(20) NOT NULL,
	  count_gumballs int(11) NOT NULL,
	  model_number varchar(255) NOT NULL,
	  serial_number varchar(255) NOT NULL,
	  PRIMARY KEY (id),
	  UNIQUE KEY serial_number (serial_number)
	);

	insert into gumball ( id, version, count_gumballs, model_number, serial_number )
	values ( 1, 0, 1000, 'M102988', '1234998871109' ) ;
 
 	select * from gumball ;


