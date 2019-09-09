
### Installing Java 8 and Tomcat 7 on AWS

```
    REF:  
    https://readlearncode.com/cloud/amazon-free-usage-tier-installing-tomcat-7-on-an-ec2-linux-instance/
    
    Step 1:     Launch EC2 Free-Tier Instance
    
                AMI: Amazon Linux AMI
                Type: t2.small (2GB RAM)
                VPC: cmpe281
                Subnet: Public
                Auto Assigned Public IP: Enabled
                Create new SG: tomcat-grails
                Open Ports: 22, 80, 8080
                Key Pair: cmpe281-us-west-1
                
    Step 2:     SSH into EC2 Instance via Public IP
    
    Step 3:     Install Java 8
    
                sudo yum install java-1.8.0-openjdk-devel
                
                ** Select Java 8 Option for: **
                
                sudo /usr/sbin/alternatives --config java
                sudo /usr/sbin/alternatives --config javac
                
                NOTE:  
                
                JAVA_HOME = /usr/lib/jvm/java-1.8.0-openjdk.x86_64
                
    
    Step 4:     Install Tomcat 7
    
                REF:  https://tomcat.apache.org/download-70.cgi

                wget https://archive.apache.org/dist/tomcat/tomcat-7/v7.0.96/bin/apache-tomcat-7.0.96.tar.gz
                tar zxpvf apache-tomcat-7.0.96.tar.gz
                sudo mv apache-tomcat-7.0.96 /usr/share

                To configure Tomcat to launch automatically create 
                a file called tomcat in the directory 
                /etc/rc.d/init.d/ with the following contents:
                    
                        #!/bin/sh
                        # Tomcat init script for Linux.
                        #
                        # chkconfig: 2345 96 14
                        # description: The Apache Tomcat servlet/JSP container.
                        JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk.x86_64
                        CATALINA_HOME=/usr/share/apache-tomcat-7.0.96
                        export JAVA_HOME CATALINA_HOME
                        exec $CATALINA_HOME/bin/catalina.sh $*
                
                Set Init Scrip Permissions
                
                        sudo chmod 755 /etc/rc.d/init.d/tomcat
                        sudo chkconfig --level 2345 tomcat on
                        
                Manual Run of Tomcat
                
                        /etc/rc.d/init.d/tomcat start
                        /etc/rc.d/init.d/tomcat stop

    Step 5:     Config Tomcat Users
    
                File: /usr/share/apache-tomcat-7.0.96/conf/tomcat-users.xml
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
    
                NOTES (To Deploy):

                Go to EC2 Public IP on port 8080
                Go to "Manager App" to Deploy your WAR File.

    Step 6:     Install MySQL Command Line Client
    
                REF:  http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/install-LAMP.html
                NOTE: Only Install MySQL from Instructions Above.
                
                sudo yum install -y mysql56 

    Step 7:     MySQL Command Line
    
                REF: https://dev.mysql.com/doc/refman/5.6/en/mysql.html
                
                mysql --user=<username> --password=<password> db_name --host <host ip>              

```                

### Installing MySQL on a Private DB Instance

```
    Step 1:     Launch EC2 Free-Tier Instance
    
                AMI: Amazon Linux AMI
                Type: t2.micro 
                VPC: cmpe281
                Subnet: Private
                Create new SG: db-mysql
                Open Ports: 3306
                Key Pair: cmpe281-us-west-1
                
    Step 2:     SSH into EC2 Instance via Public IP (of Tomcat Instance)
                And then via Private IP of MySQL Instance from Tomcat Instance.

    Step 3:     Install MySQL 

                NOTE: For the Private Instance to Reach Internet.  Associate Nat Gateway's
                Security Group with MySQL Instance.
    
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
                    Reload privilege tables now? = Y
                
    Step 4:     MySQL Command Line
    
                REF: https://dev.mysql.com/doc/refman/5.6/en/mysql.html
                                
                mysql --user=root --password
                password: ***** (enter your password)

                mysql> create database cmpe281 ;
                mysql> use cmpe281;
                mysql> show tables ;

                mysql> create user cmpe281;
                mysql> grant all on cmpe281.* to 'cmpe281'@'%'' identified by '*****';

                mysql --user=cmpe281 --password=**** cmpe281
```

### Connect to MySQL from your Tomcat Instance & Install Gumball DB Tables

```
    Step 1:     MySQL Command Line
    
                REF: https://dev.mysql.com/doc/refman/5.6/en/mysql.html
                
                mysql --user=cmpe281 --password=*** cmpe281 --host <host ip>              

    Step 2:     MySQL Commands to Install Tables

    CREATE TABLE gumball (
      id bigint(20) NOT NULL AUTO_INCREMENT,
      version bigint(20) NOT NULL,
      count_gumballs int(11) NOT NULL,
      model_number varchar(255) NOT NULL,
      serial_number varchar(255) NOT NULL,
      PRIMARY KEY (id),
      UNIQUE KEY serial_number (serial_number)
    ) ;

    insert into gumball ( id, version, count_gumballs, model_number, serial_number ) 
    values ( 1, 0, 1000, 'M102988', '1234998871109' ) ;

    select * from gumball ;    

```

### Install SDK MAN

```
    Follow Instructions Here:  http://sdkman.io/
```

### Install Groovy & Grails Locally (on your Labtop/Desktop)

```
    Note: assuming you already have Java JDK 7 or 8 Installed
    
    sdk ls grails
    sdk install grails 4.0.0
    sdk current

    grails --version

	| Grails Version: 4.0.0
	| JVM Version: 1.8.0_181
```

### Config Grails Database Connection for Production

```
    Update Grails Project:  gumball-v1 
    Update Database Config: grails-app/conf/application.yml 

    To Connector to your AWS MySQL DB.  

```

### Generate and Deploy Application WAR file

```
    In your Grails Project Root Folder, Run Command:
    
        grails war
        
    Deploy Generated WAR file in:
    
        build/libs (folder)
        
    Note: To Deploy, SCP War file to EC2 Instance and Copy into Tomcat's  "webapps" folder.  
    
    This should be in the folder:
    
        /usr/share/apache-tomcat-7.0.96/webapps
```


### (Optional) Using MySQL RDS instead of Local MySQL Instance

```
    AWS RDS Dev/Test (Free Tier)
    
    DB Instance Class:  db.t2.micro
    Multi-AZ?:          No
    Storage:            SSD / 5GB
    DB Instance ID:     CMPE281
    Master User Name:   admin
    Master Password:    *********
    
    VPC:                cmpe281
    Subnet:             New DB Subnet
    Public Access:      Yes
    AZ:                 us-west-1a
    Security Group:     New Sec Group
    DB Name:            CMPE281
    Auto Backups:       Off (Zero Days Retention)

    mysql --user=admin --password --host=<RDS MySQL Host>
                
```
