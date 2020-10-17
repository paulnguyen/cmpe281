
* https://docs.konghq.com/install/aws-linux/
* https://docs.konghq.com/install/centos/
* https://docs.konghq.com/1.3.x/network/#ports

## Deploy AWS AMI / Free Tier


1. AMI: "Amazon Linux AMI 2" 
2. Instance Type: t2.micro
3. VPC: cmpe281
4. Network: public subnet
5. Auto Public IP: yes
6. Security Group: kong 
7. SG Open Ports: 8000, 8001
8. Key Pair: cmpe281-us-west-2


## Install via Yum Repository 

  sudo yum update -y
  sudo yum install -y wget
  sudo amazon-linux-extras install -y epel
  wget https://bintray.com/kong/kong-rpm/rpm -O bintray-kong-kong-rpm.repo
  sed -i -e 's/baseurl.*/&\/amazonlinux\/amazonlinux'/ bintray-kong-kong-rpm.repo
  sudo mv bintray-kong-kong-rpm.repo /etc/yum.repos.d/
  sudo yum update -y
  sudo yum install -y kong-1.3.0


## Configure Kong in DB-Less Mode

* https://docs.konghq.com/1.3.x/db-less-and-declarative-config/

# generate a kong.yml
kong config init
sudo mv kong.yml /etc/kong

# Edit your kong.conf file. 
sudo cp /etc/kong/kong.conf.default /etc/kong/kong.conf
sudo vi /etc/kong/kong.conf

# Set database option to off in kong.conf
database = off

# Set the declarative_config /etc/kong/kong.yml in kong.conf
declarative_config = /etc/kong/kong.yml

# Enable api proxy access in kong.conf
proxy_listen = 0.0.0.0:8000, 0.0.0.0:8443 ssl

## Configure Upstream API to Proxy your Network ELB using DNS Name

* https://docs.konghq.com/1.3.x/admin-api

vi /etc/kong/kong.yml (example)

===== kong.yml file =====

  _format_version: "1.1"

  services:
  - name: <api>
    protocol: http
    host: <dns namne to your elb>
    port: <your elb port>
    path: /
    plugins:
    - name: key-auth
    routes:
    - name: <api>
      paths:
      - /

  consumers:
  - username: apiclient
    keyauth_credentials:
    - key: <your api key>

===== kong.yml file =====


## Check Config 
kong config -c /etc/kong/kong.conf parse /etc/kong/kong.yml


## Start Kong

sudo /usr/local/bin/kong start -c /etc/kong/kong.conf 
sudo /usr/local/bin/kong stop


## Check Kong Admin API

curl -i http://localhost:8001/status


