# Testing Partition tolerance in Riak DB 
Riak supports AP in CAP theorem during a network partition. We are going to setup a cluster of 4 riak nodes using amazon aws. 

### Setup Riak on AWS
Create 4 riak nodes using the following steps. 2 nodes will be in public subnet us-east-1f and other two will be in us-east-1a.

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
'execute sudo riak-admin cluster join riak@firstNodeIp on other three nodes'
sudo riak-admin cluster plan
sudo riak-admin cluster status
sudo riak-admin cluster commit
```





