
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



