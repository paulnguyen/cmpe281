# Introduction
Testing partition tolerance in mongodb. As mongodb support CP of CAP theorem which states that in case of any partition, the data will remain consistent across the nodes in a distributed system. This article will walk you through setting up the cluster of nodes running mongodb, creating a network partition and runs test cases in the partition mode and normal mode of a cluster.

### Setup EC2 Instances:
-----------------------------
-   In amazon management console, set up the free tier ubuntu instance and install the mongo DB on an instance.
-   Create the amazon AMI image.
-   Launch 4 more instance using the image.
-   Name the instances as primary, secondary1, secondary2, secondary3,
    secondary4.

Following the steps, you can setup instances with mongodb running.

|                   |               |
| ----------------  | ------------- |
| Launch Instance   | Ubuntu Server 16.04 LTS (HVM). |
| Instance Type   | T2.micro                                |
| VPC             | Cmpe-281                                |
| Assign auto IP  | Disable*Assign Elastic IP to the instance.* |
| security group  | mongodb cluster                         |
|                 | Add Inbound rules. Open ports 22, 27017 |

### Install Mongo DB on Instance:
-----------------------------
Do the following steps to setup mongodb on instances.
-   sudo apt-key adv \--keyserver hkp://keyserver.ubuntu.com:80 \--recv
    9DA31620334BD75D9DCB49F368818C72E52529D4
-   echo \"deb \[ arch=amd64,arm64 \]
    https://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/4.0
    multiverse\" \| sudo tee /etc/apt/sources.list.d/mongodb.list
-   sudo apt update
-   sudo apt install mongodb-org

### Generate MongoDB KeyFile:
-------------------------
-   openssl rand -base64 741 \> keyFile
-   sudo mkdir -p /opt/mongodb
-   sudo cp keyFile /opt/mongodb
-   sudo chown mongodb:mongodb /opt/mongodb/keyFile
-   sudo chmod 0600 /opt/mongodb/keyFile

### Configure MongoDB:
------------------
-   sudo vi /etc/mongod.conf
-   replace bindIp with 0.0.0.0
-   uncomment security and write keyFile : /opt/mongodb/keyFile.
-   Uncomment replication and write replSetName : cmpe281
### Create Mongod Service:
---------------
-   sudo vi /etc/systemd/system/mongod.service. Add the following text
    in it.
```
 [Unit]
Description=High-performance, schema-free document-oriented database
After=network.target
[Service]User=mongodb
ExecStart=/usr/bin/mongod \--quiet \--config /etc/mongod.conf
[Install\]
WantedBy=multi-user.target
Now Enable Mongo Service
```
-   sudo systemctl enable mongod.service
-   sudo service mongod restart
-   sudo service mongod status

### Create Replica Set:
-------------------
-   Create AMI image of the instance in aws.
    - AMI: mongodb
-   Launch 4 instances using that image and allocate the elastic ip with each. Name the instances as
    - primary,secondary1,secondary2,secondary3,sscondary4.
-   Edit /etc/hosts in each instance and add the ip addresses of all instances that needs to be in replication set.

|                                |                        |
|--------------------------------|----------------------  |
|   54.145.195.42                |   primary              |
|   3.81.242.72                  |   secondary1           |
|   3.209.66.95                  |   secondary2           |
|   3.85.252.30                  |   secondary3           |
|   52.202.192.206               |   secondary4           |

-   On primary node hit the following command to initiate the replica
    set.

```
rs.initiate({ _id: "cmpe281", members: [ {_id:0, host:"54.145.195.42:27017"}, {_id:1, host:"3.81.242.72:27017"}, {_id:2, host: "3.209.66.95:27017"}, {_id:3, host:"3.85.252.30:27017"}, {_id:4, host:"52.202.192.206:27017"} ] })
```
-   you can check that every node is in replica set with rs.status().
-   On every secondary node, run rs.slave() to make secondary nodes
    slaves to get replicated from primary.

# Experiments:
Going to perform several test cases to see the data in node during normal mode when there is no partition and in a partition mode. Below are the test cases done:
### No Partition Mode
----

NodeJs code is written to insert,update and retrieve the data from the db. Mocha is used to write the test cases for operations in nodejs.

**Test Case 1:**
Inserting data into the replica set using ```mocha -g 'insert' ```

**Results**: The data will be inserted into all nodes in a replicaset. Check the data on all instances and the results are consistent.
---
**Test Case 2:**
Updating data into the replica set using ```mocha -g 'update' ```
**Results**: 
The data will be updated into all nodes in a replicaset. Check the data on all instances and the results are consistent.

### Partition Mode
---
Creating a network partition using iptables command. Disconnect the primary node using the following commands:
```
sudo iptables -A INPUT -s 3.85.252.30 -j DROP
sudo iptables -A INPUT -s 3.209.66.95 -j DROP
sudo iptables -A INPUT -s 3.81.242.72 -j DROP
sudo iptables -A INPUT -s 52.202.192.206 -j DROP
```
**Test Case 1:**
Disconnect the primary node from the replicaset using above commands on primary node. Test the node is disconnected using ```rs.status()``` to see the health is down for that node.

**Result:** 
Primary node will get down and other node will become primary and data will be inserted trough that node. The data will be replicated on all other nodes in a replica set except the disconnected node.
---
**Test Case 2:**
Update the record in the replicaset using ```mocha -g 'update' ```
**Result:** The data will be updated on all nodes in the replicaset except the disconnected node i.e primary. So we will be able to read ```stale data``` from that node.
**Test Case 3:**
Now heal the partition by using below commands on the disconnected node.
```
sudo iptables -D INPUT -s 3.85.252.30 -j DROP
sudo iptables -D INPUT -s 3.209.66.95 -j DROP
sudo iptables -D INPUT -s 3.81.242.72 -j DROP
sudo iptables -D INPUT -s 52.202.192.206 -j DROP
```
**Result:** The disconnected node will become secondary and by using ```rs.slaveOk()```, it will become consistent with the replicaset and will get the updated data.



