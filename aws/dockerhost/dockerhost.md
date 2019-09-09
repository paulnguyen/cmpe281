

### AWS EC2 Docker Host

* https://runnable.com/docker/getting-started/
* https://docs.docker.com/install/linux/docker-ce/centos/

### AMI Marketplace Image

* CentOS 7 (x86_64) - with Updates HVM
* https://aws.amazon.com/marketplace/pp/B00O7WM7QW/ref=mkt_ste_catgtm_osslp

	1. AMI:            	CentOS 7 (x86_64)
	2. Instance Type:   t2.micro
	3. VPC:             cmpe281
	4. Network:         public subnet
	5. Auto Public IP:  yes (or no and use elastic ip)
	6. Security Group:  docker 
	7. SG Open Ports:   22, 80, 8080
	8. Key Pair:        your key pair (i.e. cmpe281-us-west-2 or cmpe281-us-east-1)


## Install Docker

* https://docs.docker.com/install/linux/docker-ce/centos/#install-docker-ce

<pre>
	sudo yum install -y yum-utils device-mapper-persistent-data lvm2
	sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
	sudo yum install docker-ce
	sudo systemctl start docker
	sudo systemctl is-active docker
</pre>

## Test Docker Install (Run Hello Container)

	sudo docker run hello-world

	sudo docker login
	sudo docker run -dt -p 80:3000 paulnguyen/gumball:goapi-v1.0

	http://<your-docker-host-public-ip>/ping
	curl http://13.56.137.117/gumball

## Copy Docker Admin Script (admin.sh) to your Docker Host and CleanUp Local Images

```
============================================
         D O C K E R     A D M I N          
============================================
[i] images     - Show Docker Images         
[p] ps         - Show Running Containers    
[c] cleanup    - Remove Local Images        
 
[X] Exit Menu                               
 
Selection: c
```


