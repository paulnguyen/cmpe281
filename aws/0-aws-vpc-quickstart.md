
# Amazon VPC: Quick Start Reference Deployment

```
    DOC:    http://docs.aws.amazon.com/quickstart/latest/vpc/welcome.html
            http://docs.aws.amazon.com/quickstart/latest/vpc/architecture.html

    TOOLS:  http://www.ipaddressguide.com/cidr
```

## Cloud Formation Quick Start Template

```
    S3 Template:  https://s3.amazonaws.com/quickstart-reference/aws/vpc/latest/templates/aws-vpc.template

    Stack Name:                 production
    Availability Zones:         us-west-2a, us-west-2b, us-west-2c  (3 Zones)

    VPC CIDR:                   10.0.0.0/16     (65536 Hosts)

    Private Subnet 1A:          10.0.0.0/19     (8192  Hosts)
    Private Subnet 2A:          10.0.32.0/19    (8192  Hosts)
    Private Subnet 3A:          10.0.64.0/19    (8192  Hosts)
    Private Subnet 4A:          10.0.96.0/19    -- unused --

    Public  Subnet 1A:          10.0.128.0/20   (4096  Hosts)
    Public  Subnet 2A:          10.0.144.0/20   (4096  Hosts)
    Public  Subnet 3A:          10.0.160.0/20   (4096  Hosts)
    PUblic  Subnet 4A:          10.0.176.0/20   -- unused --

    Create additional private 
    Subnets with dedicated 
    network ACLs:               FALSE

    EC2 Key Pair:               pnguyen-us-west-2
    NAT Instance Type:          t2-small

    Tags:                       None
    IAM Role:                   None (Defaults to Account)
```

## Security Groups

```
    DMZ (Public SG):            dmz-sec-group
```

## Linux AMI Image

```
    AMI Image:                  aws-linux-template
```

## ELB / Launch Config / Auto Scale Group

```
    ELB:                        elb-classic     (Classic AWS ELB)
                                elb-gateway     (New Application ELB)

    Launch Configs:             aws-linux-launch

    Auto Scale Groups:          aws-linux-autoscale 
                                3 AZ's
                                3 Public Subnets 
                                Keep at Initial Size

    ELB Gateway:                Name:               elb-gateway
                                VPC:                production
                                AZ's:               2 AZ's Public
                                Sec Group:          dmz-sec-group      
                                Target Group:       aws-linux-1, aws-linux-2 
                                                    (HTTP/80) | Health Check /index.html
                                Register Targets:   add inststances from auto-scale group
                                Listener Rules:     aws-linux-1 (/loadtest.php) | us-west-2a
                                                    aws-linux-2 (/fibonacci.php) | us-west-2b

```

## AWS DNS (Route 53) | Creating Sub Domain

```
    http://docs.aws.amazon.com/Route53/latest/DeveloperGuide/CreatingNewSubdomain.html

    Subdomain:                  aws.nguyenresearch.com

    Record Set:
                                aws.nguyenresearch.com | A | 54.201.122.231 | TTL 300
                                aws.nguyenresearch.com. | NS | TTL 172800
                                    ns-1441.awsdns-52.org. 
                                    ns-205.awsdns-25.com. 
                                    ns-1732.awsdns-24.co.uk. 
                                    ns-673.awsdns-20.net.    
                                aws.nguyenresearch.com. | SOA | ns-1441.awsdns-52.org. awsdns-hostmaster

    Update Primany DNS Zone adding NS records for Subdomain.

```                                








