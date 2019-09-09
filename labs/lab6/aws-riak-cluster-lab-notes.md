
### Riak Nodes (Private IPs)

```
10.0.1.147	riak-node1
10.0.1.28	riak-node2
10.0.1.42	riak-node3
```

## Cluster Plan Before Commit

```
ec2-user@ip-10-0-1-147 ~]$ sudo riak-admin cluster plan
=============================== Staged Changes ================================
Action         Details(s)
-------------------------------------------------------------------------------
join           'riak@10.0.1.28'
join           'riak@10.0.1.42'
-------------------------------------------------------------------------------


NOTE: Applying these changes will result in 1 cluster transition

###############################################################################
                         After cluster transition 1/1
###############################################################################

================================= Membership ==================================
Status     Ring    Pending    Node
-------------------------------------------------------------------------------
valid     100.0%     34.4%    'riak@10.0.1.147'
valid       0.0%     32.8%    'riak@10.0.1.28'
valid       0.0%     32.8%    'riak@10.0.1.42'
-------------------------------------------------------------------------------
Valid:3 / Leaving:0 / Exiting:0 / Joining:0 / Down:0

WARNING: Not all replicas will be on distinct nodes

Transfers resulting from cluster changes: 42
  21 transfers from 'riak@10.0.1.147' to 'riak@10.0.1.28'
  21 transfers from 'riak@10.0.1.147' to 'riak@10.0.1.42'

```


## Cluster Status Before Commit

```
ec2-user@ip-10-0-1-147 ~]$ sudo riak-admin cluster status
---- Cluster Status ----
Ring ready: true

+---------------------+-------+-------+-----+-------+
|        node         |status | avail |ring |pending|
+---------------------+-------+-------+-----+-------+
|     riak@10.0.1.28  |joining|  up   |  0.0|  --   |
|     riak@10.0.1.42  |joining|  up   |  0.0|  --   |
| (C) riak@10.0.1.147 | valid |  up   |100.0|  --   |
+---------------------+-------+-------+-----+-------+

Key: (C) = Claimant; availability marked with '!' is unexpected
```


## Finalize Riak Cluster

```
[ec2-user@ip-10-0-1-147 ~]$  sudo riak-admin cluster commit
Cluster changes committed

ec2-user@ip-10-0-1-147 ~]$ sudo riak-admin member_status
================================= Membership ==================================
Status     Ring    Pending    Node
-------------------------------------------------------------------------------
valid      87.5%     34.4%    'riak@10.0.1.147'
valid       9.4%     32.8%    'riak@10.0.1.28'
valid       3.1%     32.8%    'riak@10.0.1.42'
-------------------------------------------------------------------------------
Valid:3 / Leaving:0 / Exiting:0 / Joining:0 / Down:0

ec2-user@ip-10-0-1-147 ~]$ sudo riak-admin cluster status
---- Cluster Status ----
Ring ready: false

+---------------------+------+-------+-----+-------+
|        node         |status| avail |ring |pending|
+---------------------+------+-------+-----+-------+
| (C) riak@10.0.1.147 |valid |  up   | 62.5|  34.4 |
|     riak@10.0.1.28  |valid |  up   | 20.3|  32.8 |
|     riak@10.0.1.42  |valid |  up   | 17.2|  32.8 |
+---------------------+------+-------+-----+-------+

Key: (C) = Claimant; availability marked with '!' is unexpected
```


