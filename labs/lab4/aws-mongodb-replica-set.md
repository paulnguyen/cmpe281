
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






