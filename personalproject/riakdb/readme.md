# Testing Partition tolerance in Riak DB 
Riak supports AP in CAP theorem during a network partition. We are going to setup a cluster of 5 riak nodes using amazon aws. 

### Setup Riak on AWS
Create 4 riak nodes using the following steps. Three nodes will be in public subnet us-east-1f and other two will be in us-east-1a.

|                   |               |
| ----------------  | ------------- |
| Launch Instance   | Riak KV 2.2 series 64 bit  |
| VPC  | CMPE-281 |
|Network| Public Subnet|
|Assign auto IP | enable |
|Security Group |riak-cluster (open ports-22,8087,8098)|

### Cluster Setup
```sh
ssh to every node and update riak.conf in /etc/riak/
listener.http.internal = 10.0.0.165:8098
listener.protobuf.internal = 10.0.0.165:8087
nodename = riak@100.27.49.78
'execute sudo riak-admin cluster join riak@firstNodeIp on other four nodes'
sudo riak-admin cluster plan
sudo riak-admin cluster status
sudo riak-admin cluster commit
```
### Java Riak Client
Java code is used to write the data into the database cluster using riak java client. While the writes are being performed on the databse, we will create a network partition and isolate a node from the rest of the cluster. We will check the data in that node before and after the partition is done. 
### Network Partition
Execute the following command on ec2 instances shell to disconnect the node.

```sh
sudo iptables -A INPUT -s 10.0.2.183 -j DROP
sudo iptables -A INPUT -s 3.93.184.33 -j DROP
```

After the java code completely finished writing into the cluster, we will heal the partition by using following commands on ec2 instances.
```sh
sudo iptables -D INPUT -s 10.0.2.183 -j DROP
sudo iptables -D INPUT -s 3.93.184.33 -j DROP
```
### Experiment Results
- In normal mode when there is no partition, the data gets replicated to all nodes in a cluster and data is consistent.
- When partition occurs, the node that gets disconnected from the cluster or gets down does not have the data that got written to the cluster when it was down. We could not read the data from the node and it returned the error, not sufficient nodes. 
- As soon as we heal the partition, the node gets the current data that is in the cluter and we are able to read the data from that node.




