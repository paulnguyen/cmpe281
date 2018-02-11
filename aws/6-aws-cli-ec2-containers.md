### AWS CLI

```
    https://aws.amazon.com/cli/
    http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-welcome.html
    http://docs.aws.amazon.com/cli/latest/userguide/installing.html

    pip install --upgrade --user awscli
    pip uninstall awscli
    
    which aws
    /usr/local/bin/aws

    aws --version
    aws-cli/1.11.66 Python/2.7.13 Darwin/16.4.0 botocore/1.5.29
    
    aws configure --profile sjsu
    AWS Access Key ID [None]: <your access key>
    AWS Secret Access Key [None]: <your secret key>
    Default region name [None]: us-west-1
    Default output format [None]: text
    
    cat ~/.aws/config 
    cat ~/.aws/credentials
``` 

### AWS EC2 Containers

```
    https://aws.amazon.com/documentation/ecs/
    http://docs.aws.amazon.com/AmazonECS/latest/developerguide/Welcome.html
    https://github.com/paulnguyen/cmpe281/tree/master/restlet/starbucks_v3

    http://docs.aws.amazon.com/AmazonECS/latest/developerguide/get-set-up-for-amazon-ecs.html
    http://docs.aws.amazon.com/AmazonECS/latest/developerguide/private-auth.html
    http://docs.aws.amazon.com/AmazonECS/latest/developerguide/ecs-agent-config.html#ecs-config-s3
```
    
### AWS Container Getting Started

```
    http://docs.aws.amazon.com/AmazonECS/latest/developerguide/ECS_GetStarted.html
    https://us-west-1.console.aws.amazon.com/ecs/home?region=us-west-1#/firstRun
    
    1. Click the "Get Started" Button
    
        [x] Deploy a sample application onto an Amazon ECS Cluster
        [x] Store container images securely with Amazon ECR 

    2. Repository Name: cmpe281
    
       Sample URI: 060340690398.dkr.ecr.us-west-1.amazonaws.com/cmpe281
       
    3. Build, Tag and Push Docker Image

        3.1)    Retrieve the docker login command that you can use to 
                authenticate your Docker client to your registry:
        
                aws ecr get-login --region us-west-1
        
        3.2)    Run the docker login command that was returned in the previous step.
        
        3.3)    Build your Docker image using the following command. For information 
                on building a Docker file from scratch see the instructions here. 
                You can skip this step if your image is already built:
        
                docker build -t cmpe281 .
        
        3.4)    After the build completes, tag your image so you can push the 
                image to this repository:
        
                docker tag cmpe281:latest 060340690398.dkr.ecr.us-west-1.amazonaws.com/cmpe281:latest
                
        3.5)    Run the following command to push this image to your newly created AWS repository:
        
                docker push 060340690398.dkr.ecr.us-west-1.amazonaws.com/cmpe281:latest

    4. Create a task definition

            Task Definition Name:       starbucks-api
            Container Name:             starbucks-api
            Image:                      060340690398.dkr.ecr.us-west-1.amazonaws.com/cmpe281:latest
            Memory Limits (Hard):       300 (MBs)
            Port Mappings:
                Host Port               9090
                Container Port          9090
                Protocol                TCP
                
    5.  Configure Service

            Service Name:               starbucks-api
            Desired number of tasks:    2
            Container name (host port): starbucks-api:9090
            ELB listener protocol:      http
            ELB listener port:          9090
            ELB health check:           http:9090/
            Service IAM role:           ecsServiceRole
            
    6.  Configure Cluster

            Cluster Name:               starbucks-api
            EC2 instance type:          t2.micro
            Number of instances:        2
            Key Pair:                   cmpe281-us-west-1
            Security Group:             Allowed ingress source: Select "Anywhere"
            Container instance 
                     IAM role:          Create New Role (ecsServiceRole)
            
            
    7. View Service

            ELB DNS Name:           C2Contai-EcsElast-190K4W5MPZM6I-154655402.us-west-1.elb.amazonaws.com   

```     
            
