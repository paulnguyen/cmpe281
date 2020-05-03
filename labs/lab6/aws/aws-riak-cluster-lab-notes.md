
### Riak Nodes (Private IPs)

```
10.0.1.103	riak-node1
10.0.1.48	riak-node2
10.0.1.193	riak-node3
```

## Cluster Plan Before Commit

```
[ec2-user@ip-10-0-1-103 ~]$   sudo riak-admin cluster plan
=============================== Staged Changes ================================
Action         Details(s)
-------------------------------------------------------------------------------
join           'riak@10.0.1.193'
join           'riak@10.0.1.48'
-------------------------------------------------------------------------------


NOTE: Applying these changes will result in 1 cluster transition

###############################################################################
                         After cluster transition 1/1
###############################################################################

================================= Membership ==================================
Status     Ring    Pending    Node
-------------------------------------------------------------------------------
valid     100.0%     34.4%    'riak@10.0.1.103'
valid       0.0%     32.8%    'riak@10.0.1.193'
valid       0.0%     32.8%    'riak@10.0.1.48'
-------------------------------------------------------------------------------
Valid:3 / Leaving:0 / Exiting:0 / Joining:0 / Down:0

WARNING: Not all replicas will be on distinct nodes

Transfers resulting from cluster changes: 42
  21 transfers from 'riak@10.0.1.103' to 'riak@10.0.1.193'
  21 transfers from 'riak@10.0.1.103' to 'riak@10.0.1.48'
```


## Cluster Status Before Commit

```
[ec2-user@ip-10-0-1-103 ~]$ sudo riak-admin cluster status
---- Cluster Status ----
Ring ready: true

+---------------------+-------+-------+-----+-------+
|        node         |status | avail |ring |pending|
+---------------------+-------+-------+-----+-------+
|     riak@10.0.1.193 |joining|  up   |  0.0|  --   |
|     riak@10.0.1.48  |joining|  up   |  0.0|  --   |
| (C) riak@10.0.1.103 | valid |  up   |100.0|  --   |
+---------------------+-------+-------+-----+-------+

Key: (C) = Claimant; availability marked with '!' is unexpected
```


## Finalize Riak Cluster

```
[ec2-user@ip-10-0-1-103 ~]$ sudo riak-admin cluster commit
Cluster changes committed
[ec2-user@ip-10-0-1-103 ~]$ 
[ec2-user@ip-10-0-1-103 ~]$ sudo riak-admin member_status
================================= Membership ==================================
Status     Ring    Pending    Node
-------------------------------------------------------------------------------
valid      87.5%     34.4%    'riak@10.0.1.103'
valid       9.4%     32.8%    'riak@10.0.1.193'
valid       3.1%     32.8%    'riak@10.0.1.48'
-------------------------------------------------------------------------------
Valid:3 / Leaving:0 / Exiting:0 / Joining:0 / Down:0
[ec2-user@ip-10-0-1-103 ~]$ 
[ec2-user@ip-10-0-1-103 ~]$ sudo riak-admin cluster status
---- Cluster Status ----
Ring ready: true

+---------------------+------+-------+-----+-------+
|        node         |status| avail |ring |pending|
+---------------------+------+-------+-----+-------+
| (C) riak@10.0.1.103 |valid |  up   | 75.0|  34.4 |
|     riak@10.0.1.193 |valid |  up   | 17.2|  32.8 |
|     riak@10.0.1.48  |valid |  up   |  7.8|  32.8 |
+---------------------+------+-------+-----+-------+

Key: (C) = Claimant; availability marked with '!' is unexpected
[ec2-user@ip-10-0-1-103 ~]$ 
```


