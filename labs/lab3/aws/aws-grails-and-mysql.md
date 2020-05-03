

### Pre-Req:  Install Jumpbox and Dockerhost

- https://github.com/paulnguyen/cmpe281/blob/master/jumpbox/aws-jumpbox.md
- https://github.com/paulnguyen/cmpe281/blob/master/aws/dockerhost/dockerhost.md

- Install Docker Toolbox: 

    - https://www.docker.com/products/docker-toolbox
    - https://www.docker.com/docker-mac
    - https://www.docker.com/docker-windows
    - https://www.docker.com/docker-ubuntu

    NOTE:  This lab works best on Mac or Linux. 
    If you are using a Windows Machine, it is best to use
    the Docker Toolbox Option to run Docker in a Linux VM. 

- Register for Docker Hub Account:

    - https://hub.docker.com/


### Installing MySQL on a Private DB Instance

```
    Step 1:     Launch EC2 Free-Tier Instance
    
                AMI: Amazon Linux AMI
                Type: t2.micro 
                VPC: cmpe281
                Subnet: Private
                Create new SG: db-mysql
                Open Ports: 3306
                Key Pair: your key pair (i.e. cmpe281-us-west-2 or cmpe281-us-east-1)
                
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
                mysql> grant all on cmpe281.* to 'cmpe281'@'%' identified by '*****';
                mysql> flush privileges ; 

                mysql --user=cmpe281 --password=**** cmpe281
```            

### Connect to MySQL from your Jumpbox & Install Gumball DB Tables

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

### Generate Application WAR file

```
    In your Grails Project Root Folder, Run Command:
    
        grails war
        
    Confirm the Generated WAR file in:
    
        build/libs/gumball-v1-1.0.war
```


### Build and Push Docker Image to your Docker Hub Account


    Use Script:  docker.sh 

```
    ============================================
              D O C K E R   M E N U             
    ============================================
    > grails-gumball - /grails-gumball:v1.0 
     
    [1] login      - Login to Docker            
    [2] images     - Show Docker Images         
    [3] build      - Build Container Image      
    [4] run        - Run Container              
    [5] pull       - Pull Container Image       
    [6] push       - Push Build to Docker Hub   
    [7] ps         - Show Running Containers    
    [8] rmi        - Remove Container Image     
    [9] release    - Release to Docker Hub      
     
    [+] More Options                        
    [X] Exit Menu                              
     
    Selection: 
```

### Deploy Docker Image on your Docker Host

```
    docker run --restart always --name grails-gumball-v1 -td -p 8080:8080 <your docker account>/grails-gumball:v1.0 
    docker ps
```

### Expand Deployment to two Docker Hosts with a Network ELB

    - Deploy Gumball (V1) Grails App into a Two Tomcat EC2 Instances 
      (connecting to the same EC2 MySQL Instance). 
      NOTE: This does not have to be "Auto Scale".
    
    - Deploy Gumball (V2) Grails App into a Two Tomcat EC2 Instances 
      (connecting to the same EC2 MySQL Instance). 
      NOTE: This does not have to be "Auto Scale".

    - Configure a Load Balancer (Network ELB) in front of your two 
      Docker Host Instances running Gumball V1 & V2.

    - Did the Grails Gumball V1 App work as expected under load balancing?

    - Did the Grails Gumball V2 App work as expected under load balancing?



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
