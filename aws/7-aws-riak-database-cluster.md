
# Setup Riak Cluster

    http://basho.com/posts/technical/riak-on-aws-deployment-options/

    http://docs.basho.com/riak/kv/2.2.3/developing/usage/
    http://docs.basho.com/riak/kv/2.2.3/setup/installing/amazon-web-services/
    http://docs.basho.com/riak/kv/2.2.3/using/running-a-cluster/#configure-the-first-node
    http://docs.basho.com/riak/kv/2.2.3/using/cluster-operations/adding-removing-nodes/
    http://docs.basho.com/riak/kv/2.2.3/developing/usage/conflict-resolution/

    https://aws.amazon.com/marketplace/pp/B00YFZ60X2?ref=cns_srchrow

## Launch Riak Marketplace AMI (3 Nodes)

    1. AMI:             Riak KV 2.2 Series
    2. Instance Type:   t2.micro
    3. VPC:             cmpe281
    4. Network:         private subnet
    5. Auto Public IP:  no
    6. Security Group:  riak-cluster 
    7. SG Open Ports:   (see below)
    8. Key Pair:        cmpe281-us-west-1
    
    Riak Cluster Security Group (Open Ports):
    
        22 (SSH)
        8087 (Riak Protocol Buffers Interface)
        8098 (Riak HTTP Interface)
    
    You will need to add additional rules within this security group 
    to allow your Riak instances to communicate. For each port range 
    below, create a new Custom TCP rule with the source set to the 
    current security group ID (found on the Details tab).
    
        Port range: 4369
        Port range: 6000-7999
        Port range: 8099
        Port range: 9080

## Launch "Jump Box" AWS Linux AMI

    1. AMI:             Amazon Linux AMI 2018.03.0 (HVM)
    2. Instance Type:   t2.micro
    3. VPC:             cmpe281
    4. Network:         public subnet
    5. Auto Public IP:  yes
    6. Security Group:  cmpe281-dmz 
    7. SG Open Ports:   22, 80, 443
    8. Key Pair:        cmpe281-us-west-1
    

## SSH into Riak Instance (via Jump Box)

    ssh -i <key>.pem ec2-user@<public ip>  (access jump box)
    ssh -i <key>.pem ec2-user@<private ip> (access riak node)
    
    
## Setup Riak Cluster Nodes (3 Nodes)

    You will need need to launch at least 3 instances 
    to form a Riak cluster. When the instances have been 
    provisioned and the security group is configured, 
    you can connect to them using SSH or PuTTY as the ec2-user.

    For all other nodes, use the internal IP address of the first node:

        sudo riak-admin cluster join riak@<ip.of.first.node>

    After all of the nodes are joined, execute the following:

        sudo riak-admin cluster plan
        sudo riak-admin cluster status
        
    If this looks good:

        sudo riak-admin cluster commit

    To check the status of clustering use:

        sudo riak-admin member_status

    You now have a Riak cluster running on AWS.

## Sample Riak Usage Example

    curl -i http://10.0.1.116:8098/buckets?buckets=true    

    curl -v -XPUT -d '{"foo":"bar"}' \
        http://10.0.1.116:8098/buckets/bucket/keys/key1?returnbody=true

    curl -i http://10.0.1.116:8098/buckets/bucket/keys/key1







