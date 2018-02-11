# Tutorial: Use Path-Based Routing with Your Application Load Balancer

```
	DOC:  
	http://docs.aws.amazon.com/elasticloadbalancing/latest/application/tutorial-load-balancer-routing.html
```

# Tutorial: Use Microservices as Targets with Your Application Load Balancer

```
	DOC:  
	http://docs.aws.amazon.com/elasticloadbalancing/latest/application/tutorial-target-ecs-containers.html
```

## Create Target Groups

```
	aws-linux-1	Port 80	Protocol HTTP (Register one or more Instances)
	aws-linux-2	Port 80	Protocol HTTP (Register one or more Instances)
```	
	
## Create Application Load Balancer

```
	Name:				elb-gateway
	DNS name:			elb-gateway-1149191633.us-west-2.elb.amazonaws.com
	VPC:				vpc-d73698b0
	AZ's:				subnet-6ade8b32 - us-west-2c,
					subnet-9b6ca3fc - us-west-2a,
					subnet-9c6081d5 - us-west-2b
	
	Listener:			HTTP (Port 80)
		Path:			/loadtest.php		Target: aws-linux-1
		Path:			/fibonacci.php		Target: aws-linux-2
					(Default)		Target: aws-linux-1
						
	Security:			dmz-sec-group
	
	Listener:			HTTPS (Port 443)
					443 (HTTPS, 
					     ACM Certificate: 2d826bbb-7e63-4a78-9db6-51003ce4a83f) 
					forwarding to 80 (HTTP)
						
	Route 53:			Host Name:  		www.sample.net
					Alias Target:		elb-gateway
```						
