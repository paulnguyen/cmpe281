# Introduction 
Riak supports AP in CAP theorem during a network partition. We are going to setup a cluster of 4 riak nodes. We will do ***vpc peering*** to create a cluster of riak running on two different vpcs. Later we will remove the peer connection in order to create a network partition in a cluster.
### Setup VPC peering
---

We will cerate another vpc with 1 publci/private subnet having ipv4 CIDR block 172.0.0.0/16. In order to do vpc peering, we have to add ipv4 CIDR block in each other route tables. To create the peering:
|                   |               |
| ----------------  | ------------- |
| Peering connection name tag| Riak |
| VPC (Requester)*  | Riak-vpc |
| VPC (Acceptor)*  | CMPE-281 |
|select peer connections->Actions|Accept the peer connection|

**Route Tables - vpc1**

|   Destination                |    Target           |
| ----------------  | ------------- |
|10.0.0.0/16|local|
|0.0.0.0/0|	igw-084b07c61541f5a03|
| 172.0.0.0/16  | pcx-0294e43a3f331cbba  |
**Route Tables - vpc2**

|   Destination                |    Target           |
| ----------------  | ------------- |
|172.0.0.0/16|local|
|0.0.0.0/0|igw-08dcc24321d752aad|
| 10.0.0.0/16   | pcx-0294e43a3f331cbba  |

### Setup Riak on AWS
---

Create riak nodes using the following steps. Three nodes will be in public subnet in one vpc and other one will be in another vpc. We will do vpc peering to connect both vpc together in order to form a riak cluster.

|                   |               |
| ----------------  | ------------- |
| Launch Instance   | Riak KV 2.2 series 64 bit  |
| VPC  | CMPE-281/Riak-vpc |
|Network| Public Subnet|
|Assign auto IP | enable |
|Security Group |riak-cluster (open ports-22,8087,8098,ICMP ALL)|

**Setup Riak Cluster**
```sh
ssh to every node and update riak.conf in /etc/riak/
listener.http.internal = 10.0.0.165:8098 (private ip)
listener.protobuf.internal = 10.0.0.165:8087
nodename = riak@100.27.49.78 
```
***Execute sudo riak-admin cluster join riak@firstNodeIp on other three nodes***

Now on First node do the following
```
sudo riak-admin cluster plan
sudo riak-admin cluster status
sudo riak-admin cluster commit 
```

Test the cluster is running using the 
curl -i http://noe1:8098/ping (node2,node3,node4)
It should return `HTTP/1.1 200 OK`
***(You can test via postman too)***
```
sudo riak-admin cluster status
```

 |      node         |status| avail |ring |pending|
|----------------------|------|-------|-----|-------|
|     riak@10.0.0.163  |valid |  up   | 25.0|  --   |
| (C) riak@10.0.0.165  |valid |  up   | 25.0|  --   |
|      riak@10.0.0.27  |valid |  up   | 25.0|  --   |
|     riak@172.0.0.240 |valid | up | 25.0|  --  |

# Experiments
Going to perform several test cases to see the data in nodes of cluster during normal mode when there is no partition and in a partition mode. Below are the test cases done:

### No Partition Mode
----

***Test Case 1:***
Inserting data into the cluster using the following
```
curl -X POST \
  http://node3:8098/buckets/fruits/keys/1 \
  -d 'name=Apple'
```

***Result:***
The data will be replicated to all nodes in a clustert. Check the data on all instances and the results are consistent. 
```
curl -X GET http://noe1:8098/buckets/fruist/keys?keys=true (node2,node3,node4)
```
***Test Case 2:***
Now update the data with the following
```
curl -X PUT \
  http://node2:8098/buckets/fruits/keys/2 \
  -d 'name=Mango'
```
***Result:***
The data will be updated to all nodes in a cluster. Check the data on all instances and the results are consistent. 
```
curl -X GET http://noe1:8098/buckets/fruits/keys/2 (node2,node3,node4)
```
### Partition Mode
----

Now lets remove the peer connection between two vpc and perform the following tests. Node4 will get disconnected from the cluster and you can do 
```
sudo riak-admin cluster status
```
 |      node         |status| avail |ring |pending|
|----------------------|------|-------|-----|-------|
|     riak@10.0.0.163  |valid |  up   | 25.0|  --   |
| (C) riak@10.0.0.165  |valid |  up   | 25.0|  --   |
|      riak@10.0.0.27  |valid |  up   | 25.0|  --   |
|     riak@172.0.0.240 |valid | down! | 25.0|  --  |

***Test Case 1:***
Inserting data into the cluster using the following
```
curl -X POST \
  http://node2:8098/buckets/fruits/keys/5 \
  -d 'name=PineApple'
```
***Result:***
The data will be updated to all nodes in a cluster except the node4 which is down and has been partitioned. Check the data on all instances and the results are consistent. doing the folowing in node4 will return `not found`.
```
curl -X GET http://node1:8098/buckets/fruits/keys/5 (node2,node3,node4)
```

***Test Case 2:***
Update data into the cluster using the following
```
curl -X PUT \
  http://node2:8098/buckets/fruits/keys/2 \
  -d 'name=Banana'
```
***Result:***
The data will be updated to all nodes in a cluster except the node4 which is down and has been partitioned. Check the data on all instances and the results are consistent. doing the folowing in node4 will return `Mango` instead of `Banana`.
```
curl -X GET http://node1:8098/buckets/fruits/keys/5 (node2,node3,node4)
```
***Test Case 2:***
Now heal the partition by creating the peer connection again using the above steps. Check the status of node4 by

```
sudo riak-admin cluster status
```

 |      node         |status| avail |ring |pending|
|----------------------|------|-------|-----|-------|
|     riak@10.0.0.163  |valid |  up   | 25.0|  --   |
| (C) riak@10.0.0.165  |valid |  up   | 25.0|  --   |
|      riak@10.0.0.27  |valid |  up   | 25.0|  --   |
|     riak@172.0.0.240 |valid | up | 25.0|  --  |

***Result:***
Now the node4 will get the latest version of data present in the cluster and is updated with the rest of cluster.

```
curl -X GET http://node4:8098/buckets/fruits/keys?keys=true 
```
`Output:{"keys":["3","5","4","2","1"]}`

```
curl -X GET http://node4:8098/buckets/fruits/keys/2 
```
`Output:name=Banana`

# Conclusion
We have succesfully tested the partition tolerance in Riak Database.
