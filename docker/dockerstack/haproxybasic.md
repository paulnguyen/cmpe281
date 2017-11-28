

	  _   _    _      ____                        ____            _      
	 | | | |  / \    |  _ \ _ __ _____  ___   _  | __ )  __ _ ___(_) ___ 
	 | |_| | / _ \   | |_) | '__/ _ \ \/ / | | | |  _ \ / _` / __| |/ __|
	 |  _  |/ ___ \  |  __/| | | (_) >  <| |_| | | |_) | (_| \__ \ | (__ 
	 |_| |_/_/   \_\ |_|   |_|  \___/_/\_\\__, | |____/ \__,_|___/_|\___|
                                      |___/                          


# Docker Cloud HA Proxy

	https://github.com/docker/dockercloud-haproxy 

	HAproxy image that autoreconfigures itself when used in Docker Cloud
	https://cloud.docker.com/

# Stack file example: haproxybasic.yml

	web:
	  image: 'dockercloud/hello-world:latest'
	  target_num_containers: 2
	lb:
	  image: 'dockercloud/haproxy:latest'
	  links:
	    - web
	  ports:
	    - '80:80'
	  roles:
	    - global