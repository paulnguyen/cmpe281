

	 
	  ____             _               ____  _             _    
	 |  _ \  ___   ___| | _____ _ __  / ___|| |_ __ _  ___| | __
	 | | | |/ _ \ / __| |/ / _ \ '__| \___ \| __/ _` |/ __| |/ /
	 | |_| | (_) | (__|   <  __/ |     ___) | || (_| | (__|   < 
	 |____/ \___/ \___|_|\_\___|_|    |____/ \__\__,_|\___|_|\_\
	 
                                  


# Docker Cloud Stack Reference

	https://docs.docker.com/docker-cloud/apps/stack-yaml-reference/

# Stack file example: dockerstack.yml

	lb:
	  image: dockercloud/haproxy
	  links:
	    - web
	  ports:
	    - "80:80"
	  roles:
	    - global
	web:
	  image: dockercloud/quickstart-python
	  links:
	    - redis
	  target_num_containers: 4
	redis:
	  image: redis

# dockerstack.yml 

Each key defined in dockerstack.yml creates a service with that name in Docker
Cloud.  In the example above, three services are created: lb, web, and redis

# image (required)

The image used to deploy this service. This is the only mandatory key.

	image: drupal
	image: dockercloud/hello-world
	image: my.registry.com/redis

# autodestroy

Whether the containers for this service should be terminated if they stop
(default: no, possible values: no, on-success, always).

	autodestroy: always

# autoredeploy

Whether to redeploy the containers of the service when its image is updated in
Docker Cloud registry (default: false).

	autoredeploy: true

# deployment_strategy

Container distribution among nodes (default: emptiest_node, possible values:
emptiest_node, high_availability, every_node). Learn more here.

	deployment_strategy: high_availability

# dns

Specify custom DNS servers. Can be a single value or a list.

	dns: 8.8.8.8
	dns:
	  - 8.8.8.8
	  - 9.9.9.9

# dns_search

Specify custom DNS search domains. Can be a single value or a list.

	dns_search: example.com
	dns_search:
	  - dc1.example.com
	  - dc2.example.com

# environment

A list of environment variables to add in the service’s containers at launch.
The environment variables specified here override any image-defined
environment variables. You can use either an array or a dictionary format.

	Dictionary format:

	environment:
	    PASSWORD: my_password

	Array format:

	environment:
	  - PASSWORD=my_password

When you use the Docker Cloud CLI to create a stack, you can use the
environment variables defined locally in your shell to define those in the
stack. This is useful if you don’t want to store passwords or other sensitive
information in your stack file:

	environment:
	  - PASSWORD

# expose

Expose ports without publishing them to the host machine - they’ll only be
accessible from your nodes in Docker Cloud. udp ports can be specified with a
/udp suffix.

	expose:
	 - "80"
	 - "90/udp"

# extra_hosts

Add hostname mappings. Uses the same values as the docker client --add-host
parameter.

	extra_hosts:
	  - "somehost:162.242.195.82"
	  - "otherhost:50.31.209.229"

# labels

Add metadata to containers using Docker Engine labels. You can use either an
array or a dictionary.

We recommend using reverse-DNS notation to prevent your labels from
conflicting with those used by other software.

	labels:
	  com.example.description: "Accounting webapp"
	  com.example.department: "Finance"
	  com.example.label-with-empty-value: ""

	labels:
	  - "com.example.description=Accounting webapp"
	  - "com.example.department=Finance"
	  - "com.example.label-with-empty-value"

# links

Link to another service.

Either specify both the service unique name and the link alias
(SERVICE:ALIAS), or just the service unique name (which will also be used for
the alias). If a service you want to link to is part of a different stack,
specify the external stack name too.

    If the target service belongs to this stack, its service unique name is its service name.
    If the target service does not belong to any stacks (it is a standalone service), its service unique name is its service name.
    If the target service belongs to another stack, its service unique name is its service name plus the service stack name, separated by a period (.).

	links:
	 - mysql
	 - redis:cache
	 - amqp.staging:amqp

Environment variables are created for each link that Docker Cloud resolves to
the containers IPs of the linked service. More information here:
https://docs.docker.com/docker-cloud/apps/service-links/

# net

Networking mode. Only “bridge” and “host” options are supported for now.

	net: host

# pid

Sets the PID mode to the host PID mode. This turns on sharing between
container and the host operating system PID address space. Containers launched
with this (optional) flag will be able to access and be accessed by other
containers in the namespace belonging to the host running the Docker daemon.

	pid: "host"

# ports

Expose ports. Either specify both ports (HOST:CONTAINER), or just the
container port (a random host port will be chosen). udp ports can be specified
with a /udp suffix.

	ports:
	 - "80"
	 - "443:443"
	 - "500/udp"
	 - "4500:4500/udp"
	 - "49022:22"

# privileged

Whether to start the containers with Docker Engine’s privileged flag set or
not (default: false).

	privileged: true

# restart

Whether the containers for this service should be restarted if they stop
(default: no, possible values: no, on-failure, always).

	restart: always

# roles

A list of Docker Cloud API roles to grant the service. The only supported
value is global, which creates an environment variable DOCKERCLOUD_AUTH used
to authenticate against Docker Cloud API. Learn more here.

	roles:
	 - global

# security_opt

Override the default labeling scheme for each container.

	security_opt:
	  - label:user:USER
	  - label:role:ROLE

# sequential_deployment

Whether the containers should be launched and scaled in sequence (default:
false). Learn more here.

	sequential_deployment: true

# tags

Indicates the deploy tags to select the nodes where containers are created.

	tags:
	 - staging
	 - web

# target_num_containers

The number of containers to run for this service (default: 1).

	target_num_containers: 3

# volumes

Mount paths as volumes, optionally specifying a path on the host machine
(HOST:CONTAINER), or an access mode (HOST:CONTAINER:ro).

	volumes:
	 - /etc/mysql
	 - /sys:/sys
	 - /etc:/etc:ro

# volumes_from

Mount all of the volumes from another service by specifying a service unique
name.

    If the target service belongs to this stack, its service unique name is its service name.
    If the target service does not belong to any stack, its service unique name is its service name.
    If the target service belongs to another stack, its service unique name is its service name plus the service stack name, separated by “.”. Learn more here.

	volumes_from:
	 - database
	 - mongodb.staging

# Single value keys analogous to a docker run counterpart

	working_dir: /app
	entrypoint: /app/entrypoint.sh
	user: root
	hostname: foo
	domainname: foo.com
	mac_address: 02:42:ac:11:65:43
	cpu_shares: 512
	cpuset: 0,1
	mem_limit: 100000m
	memswap_limit: 200000m
	privileged: true
	read_only: true
	stdin_open: true
	tty: true

# Unsupported Docker-compose keys

Stack files (docker-cloud.yml) were designed with docker-compose.yml in mind
to maximize compatibility. However the following keys used in Compose are not
supported in Docker Cloud stackfiles:

	build
	external_links
	env_file







