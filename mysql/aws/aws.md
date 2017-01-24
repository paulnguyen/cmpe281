

       __|  __|_  )
       _|  (     /   Amazon Linux AMI
      ___|\___|___|

https://aws.amazon.com/amazon-linux-ami/2015.09-release-notes/


# Reference:

    https://dev.mysql.com/doc/refman/5.6/en/ha-vm-aws-setup.html
    https://codeforgeek.com/2015/01/nodejs-mysql-tutorial/
    
# Setup Steps:

    http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/install-LAMP.html
    https://dev.mysql.com/doc/refman/5.5/en/mysql.html

    sudo yum update -y
    sudo yum install -y mysql55-server
    sudo service mysqld start
    /usr/libexec/mysql55/mysql_secure_installation
    #set password for root to XXX 
    mysql --user=root --password=XXX
    
# Allow Remote Access:

    https://easyengine.io/tutorials/mysql/remote-access/

    CREATE DATABASE CMPE281;    
    CREATE USER 'CMPE281' IDENTIFIED BY 'XXX';
    GRANT ALL PRIVILEGES ON *.* TO 'CMPE281'@'%' IDENTIFIED BY 'XXX' WITH GRANT OPTION;
    SELECT * from information_schema.user_privileges where grantee like "'CMPE281'%";
    ##REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'CMPE281'@'%';
    GRANT ALL PRIVILEGES ON *.* TO 'cmpe281'@'%' IDENTIFIED BY 'XXX' WITH GRANT OPTION;
    FLUSH PRIVILEGES;
    mysql --user=cmpe281 --password=XXX --host=<EC2 Public IP>
   
    
    