
 

	  _____             _                _____ _                 _ 
	 |  __ \           | |              / ____| |               | |
	 | |  | | ___   ___| | _____ _ __  | |    | | ___  _   _  __| |
	 | |  | |/ _ \ / __| |/ / _ \ '__| | |    | |/ _ \| | | |/ _` |
	 | |__| | (_) | (__|   <  __/ |    | |____| | (_) | |_| | (_| |
	 |_____/ \___/ \___|_|\_\___|_|     \_____|_|\___/ \__,_|\__,_|
	                                                             

# Docker Cloud Getting Started

	https://docs.docker.com/docker-cloud/getting-started/

## Step 1 - Link Cloud AWS Account

	https://docs.docker.com/docker-cloud/getting-started/link-aws/

## Step 2 - Bring your own Node

	https://docs.docker.com/docker-cloud/getting-started/use-byon/
	https://docs.docker.com/docker-cloud/tutorials/byoh/

## Step 3 - Deploy your First Node

	https://docs.docker.com/docker-cloud/getting-started/your_first_node/

	Name:  			hello-docker-cloud
	Deploy Tags:	hello-docker-cloud
	Region:			us-west-1
	VPC:			helloworld
	Size:			t2.micro
	IAM Role:		none
	Num Nodes:		1
	Disk Size:		60 GB

## Step 4 - Deploy your First Service

	https://docs.docker.com/docker-cloud/getting-started/your_first_service/

	A service is a group of containers of the same image:tag. 

	Services make it simple to scale your application. With Docker Cloud, you simply drag a slider to change the number of containers in a service.

	Service Name:	dockercloud-helloworld
	Image:			dockercloud/hello-world 
	Image Tag:		latest
	Override Port:	80 TCP Published Dynamic
	Run Command:	 /bin/sh -c php-fpm -d variables_order="EGPCS" && 
					(tail -F /var/log/nginx/access.log &) && 
					exec nginx -g "daemon off;" (defined in image)
	Auto Restart:	Off
	Auto Destroy:	Off
	Entrypoint:		N/A
	Environment:	Default Env Vars
	Volumes:		None Configured	


	Service Name:	paulnguyen-helloworld
	Image:			paulnguyen/helloworld 
	Image Tag:		hellotag
	Run Command:	/bin/sh -c /usr/games/fortune -a | cowsay
	Auto Restart:	Off
	Auto Destroy:	Off
	Entrypoint:		N/A
	Environment:	Default Env Vars
	Volumes:		None Configured	



# References

	https://cloud.docker.com/
	https://docs.docker.com/docker-cloud/
	https://docs.docker.com/apidocs/docker-cloud/





