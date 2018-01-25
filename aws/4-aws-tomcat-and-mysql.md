### Installing Java 8 and Tomcat 7 on AWS

```
	REF:  https://readlearncode.com/cloud/amazon-free-usage-tier-installing-tomcat-7-on-an-ec2-linux-instance/
	
	Step 1: 	Launch EC2 Free-Tier Instance
	
				Type: t2.micro
				VPC: cmpe281
				Subnet: Public
				Auto Assigned Public IP: Enabled
				Create new SG: tomcat-grails
				Open Ports: 22, 80, 8080
				Key Pair: cmpe281-us-west-1
				
	Step 2:		SSH into EC2 Instance via Public IP
	
	Step 3:		Install Java 8
	
				sudo yum install java-1.8.0-openjdk-devel
				
				** Select Java 8 Option for: **
				
				sudo /usr/sbin/alternatives --config java
				sudo /usr/sbin/alternatives --config javac
				
				NOTE:  
				
				JAVA_HOME = /usr/lib/jvm/java-1.8.0-openjdk.x86_64
				
	
	Step 4:		Install Tomcat 7
	
				wget http://mirror.symnds.com/software/Apache/tomcat/tomcat-7/v7.0.75/bin/apache-tomcat-7.0.75.tar.gz
				tar zxpvf apache-tomcat-7.0.75.tar.gz
				sudo mv apache-tomcat-7.0.75 /usr/share

				To configure Tomcat to launch automatically create 
				a file called tomcat in the directory 
				/etc/rc.d/init.d/ with the following contents:
					
						!/bin/sh
						# Tomcat init script for Linux.
						#
						# chkconfig: 2345 96 14
						# description: The Apache Tomcat servlet/JSP container.
						JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk.x86_64
						CATALINA_HOME=/usr/share/apache-tomcat-7.0.75
						export JAVA_HOME CATALINA_HOME
						exec $CATALINA_HOME/bin/catalina.sh $*
				
				Set Init Scrip Permissions
				
						sudo chmod 755 /etc/rc.d/init.d/tomcat
						sudo chkconfig --level 2345 tomcat on
						
				Manual Run of Tomcat
				
						/etc/rc.d/init.d/tomcat start
						/etc/rc.d/init.d/tomcat stop

	Step 5:		Config Tomcat Users
	
				File: /usr/share/apache-tomcat-7.0.75/conf/tomcat-users.xml
				Password:  <Chose your Password>
				
				<tomcat-users>
					<role rolename="manager-script"/>
					<role rolename="manager-jmx"/>
					<role rolename="manager-status"/>
					<role rolename="admin-gui"/>
					<role rolename="manager-gui"/>
					<user username="tomcat" password="**********" 
					  roles="manager-gui,manager-status,admin-gui"/>
					<user username="tomcattools" password="**********"/>
				</tomcat-users>
	
				Go to EC2 Public IP on port 8080
				Go to "Manager App" to Deploy your WAR File.
				
	Step 6:		Install MySQL on Same Tomcat EC2 Instance
	
				REF:  http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/install-LAMP.html
				NOTE: Only Install MySQL from Instructions Above.
				
				sudo yum install -y mysql56-server 
				
				sudo service mysqld start
				sudo service mysqld stop
				sudo chkconfig mysqld on
				
				sudo mysql_secure_installation
					
					Default root password = none (hit enter)
					Set root passwrd = ***** (choose your own)
					Remove Anonymous Users = Y
					Disallow root Remote Logins = Y
					Remove Test Databases = Y
				
	Step 7:		MySQL Command Line
	
				REF: https://dev.mysql.com/doc/refman/5.6/en/mysql.html
				
				mysql --user=user_name --password=your_password db_name
				
				mysql --user=root --password
				password: ***** (enter your password)
				
				mysql> create database cmpe281 ;
				mysql> use cmpe281;
				mysql> show tables ;
```

### (Optional) Using MySQL RDS instead of Local MySQL Instance

```
	AWS RDS Dev/Test (Free Tier)
	
	DB Instance Class:	db.t2.micro
	Multi-AZ?:				No
	Storage:				SSD / 5GB
	DB Instance ID:		    CMPE281
	Master User Name:		admin
	Master Password:		cmpe281#2017
	
	VPC:					cmpe281
	Subnet:					New DB Subnet
	Public Access:			Yes
	AZ:						us-west-1a
	Security Group:		    New Sec Group
	DB Name:				CMPE281
	Auto Backups:			Off (Zero Days Retention)

	mysql --user=admin --password --host=<RDS MySQL Host>
				
```