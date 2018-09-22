
# Setup MongoDB AMI

    https://docs.mongodb.com/manual/tutorial/deploy-replica-set-with-keyfile-access-control/#deploy-repl-set-with-auth
    https://gist.github.com/calvinh8/c99e198ce5df3d8b1f1e42c1b984d7a4    
    https://eladnava.com/deploy-a-highly-available-mongodb-replica-set-on-aws/
    http://www.serverlab.ca/tutorials/linux/database-servers/how-to-create-mongodb-replication-clusters/

## Launch Ubuntu Server 16.04 LTS

    1. AMI:             Ubuntu Server 16.04 LTS (HVM)
    2. Instance Type:   t2.micro
    3. VPC:             cmpe281
    4. Network:         public subnet
    5. Auto Public IP:  no
    6. Security Group:  mongodb-cluster 
    7. SG Open Ports:   22, 27017
    8. Key Pair:        cmpe281-us-west-1

## Allocate & Assign an Elastic IP to Mongo Instance

    1. Allocate Elastic IP:     Scope VPC
    2. Name Elastic IP:         mongodb
    3. Associate Elastic IP:    Instance = Mongo EC2 Instance

## SSH into Mongo Instance

    ssh -i <key>.pem ubuntu@<public ip>

## Install MongoDB

    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6
    echo "deb [ arch=amd64 ] http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.4.list

    sudo apt-get update
    sudo apt-get install -y mongodb-org

## MongoDB Keyfile

    openssl rand -base64 741 > keyFile
    sudo mkdir -p /opt/mongodb
    sudo cp keyFile /opt/mongodb
    sudo chown mongodb:mongodb /opt/mongodb/keyFile
    sudo chmod 0600 /opt/mongodb/keyFile

## Config mongod.conf

    sudo vi /etc/mongod.conf

    1.  remove or comment out bindIp: 127.0.0.1 

        # network interfaces
        net:
            port: 27017
    ==> # bindIp: 127.0.0.1  # 

    2. Uncomment security section & add key file

    security:
        keyFile: /opt/mongodb/keyFile

    3. Uncomment Replication section. Name Replica Set = cmpe281

    replication:
        replSetName: cmpe281

    4. Create mongod.service

    sudo vi /etc/systemd/system/mongod.service

        [Unit]
            Description=High-performance, schema-free document-oriented database
            After=network.target

        [Service]
            User=mongodb
            ExecStart=/usr/bin/mongod --quiet --config /etc/mongod.conf

        [Install]
            WantedBy=multi-user.target

    5. Enable Mongo Service

        sudo systemctl enable mongod.service

    6. Restart MongoDB to apply our changes

        sudo service mongod restart


## Initialize the replica set

    rs.initiate()

## Create Admin Account

    The default MongoDB configuration is wide open, meaning anyone can access 
    the stored databases unless your network has firewall rules in place.

    Create an admin user to access the database.

        mongo

    Select admin database.

        use admin

    Create admin account.

        db.createUser( {
            user: "admin",
            pwd: "*****",
            roles: [{ role: "root", db: "admin" }]
        });

## Save to AMI Image

    AMI: mongo-ami

## Using AMI, launch two additional MongoDB Instances with their own Public Elastic IP
## And complete the Replica Set

    See Steps in:  https://gist.github.com/calvinh8/c99e198ce5df3d8b1f1e42c1b984d7a4   


## Setup Bios MySQL and MongoDB Data

    See:  https://github.com/paulnguyen/cmpe281/tree/master/labs/lab4


## Write the Equivalent Mongo Queries for the following MySQL Queries:

### 1 - Count of Records/Documents

    select count(*) from person

        
### 2 - Find Bios with Birth Date before 1950

    select first_name, last_name, birth_date 
    from person
    where birth_date < date('1950-01-01')
    
## #3 - Get a Unique Listing of all the Awards (in DB/Collection) granted

    select distinct(a.award_name)
    from person_awards pa, awards a
    where pa.award_id = a.award_id

    
## #4 - Get a Sorted Listing of all the First Names (ascending order)

    select first_name
    from person
    order by 1
    

#5 - Get a Sorted Listing of all the First Names (descending order)

    select first_name
    from person
    order by 1 desc
    

## #6 - Count the number of BIOS that don't yet have an award  

    select count(*) from person p
    where not exists 
        (select 1 from person_awards 
         where person_id = p.person_id)


### 7 - Display the System ID (Primary Key) for the BIO in Query #6

    select p.person_id from person p
    where not exists 
        (select 1 from person_awards 
         where person_id = p.person_id)

## #8 - Display names (first and last) from BIOS with 1 Contribution AND 2 Awards

    select p.first_name, p.last_name
    from person p
    where (select count(*) from contribs c where c.person_id = p.person_id) = 1
    and (select count(*) from person_awards pa where pa.person_id = p.person_id) = 2
    

### 9 - Display names (first and last) from BIOS with 1 Contributions OR 2 Awards

    select p.first_name, p.last_name
    from person p
    where (select count(*) from contribs c where c.person_id = p.person_id) = 1
    or (select count(*) from person_awards pa where pa.person_id = p.person_id) = 2    

### 10 - List all the Awards for a BIO

    select p.first_name, p.last_name, a.award_name
    from awards a, person_awards pa, person p
    where a.award_id = pa.award_id
    and p.person_id = pa.person_id
    and p.person_id = 1


