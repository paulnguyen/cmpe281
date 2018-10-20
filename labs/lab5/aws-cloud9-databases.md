
# Amazon Web Services: Cloud9 Workspace Databases 

```
    DOC:    https://aws.amazon.com/cloud9/getting-started/
            https://docs.aws.amazon.com/cloud9/latest/user-guide/ide.html
            https://docs.mongodb.com/manual/tutorial/install-mongodb-on-amazon/
            https://dev.mysql.com/doc/refman/5.6/en/ha-vm-aws-setup.html
            https://codeforgeek.com/2015/01/nodejs-mysql-tutorial/
            http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/install-LAMP.html
            https://dev.mysql.com/doc/refman/5.5/en/mysql.html            
```

### Configure the package management system (yum).

    Create a /etc/yum.repos.d/mongodb-org-3.6.repo file so that you can install MongoDB directly, using yum.

    sudo touch /etc/yum.repos.d/mongodb-org-3.6.repo
    sudo vi /etc/yum.repos.d/mongodb-org-3.6.repo

```
    [mongodb-org-3.6]
    name=MongoDB Repository
    baseurl=https://repo.mongodb.org/yum/amazon/2013.03/mongodb-org/3.6/x86_64/
    gpgcheck=1
    enabled=1
    gpgkey=https://www.mongodb.org/static/pgp/server-3.6.asc
```  

### Install MongoDB by running the following command from the Terminal:

    sudo yum install -y mongodb-org

### Create the default DB Path for Mongo in your workspace:

    sudo mkdir -p /data/db
    sudo chown ec2-user /data/db  
    ls -ld /data/db

    drwxr-xr-x 2 ec2-user root 4096 Feb 14 15:47 /data/db

### Then, from the terminal, run the following command to start Mongo:

    mongod --bind_ip=$IP --nojournal

    The output will include:
    ...
    waiting for connections on port 27017

### Now you can open the mongo shell in a new Terminal, running following command:

    mongo

### To stop the MongoDB instance press Control + C in the Terminal where mongod is running. Now have a look at the currently used database:

    $ mongo
    mongo> db
    test
                             

### MySQL Setup Steps:

    sudo yum update -y
    sudo yum install -y mysql55-server
    sudo service mysqld start

## Set password for root (and disable default settings)

    /usr/libexec/mysql55/mysql_secure_installation

### Login as root (i.e. cmpe281)

    mysql --user=root --password





