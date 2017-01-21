
 	_____             _             
	|  __ \           | |            
 	| |  | | ___   ___| | _____ _ __ 
 	| |  | |/ _ \ / __| |/ / _ \ '__|
 	| |__| | (_) | (__|   <  __/ |   
 	|_____/ \___/ \___|_|\_\___|_|   

                              

# Getting Started with Docker
    
## Run an Echo Server in Ubuntu Image with Restart Always
run-restart:
	docker run -d --restart=always ubuntu echo done

## Run with Restart on Failure (non-zero return) Max 10 Times
run-restart-10:
	docker run -d --restart=on-failure:5 ubuntu /bin/false

## SHow all Containers
ps:
	docker ps -a

## Remove a Container by Container's ID
rm:
	read -p "Enter Container ID: " id ; docker stop $$id ; docker rm $$id; 

## Download Wordpress Image
pull:
	docker pull tutum/wordpress

## Run Blog #1
run-blog1:
	docker run -d -p 10001:80 --name blog1 tutum/wordpress

## Run Blog #2
run-blog2:
	docker run -d -p 10002:80 --name blog2 tutum/wordpress

## Stop Containers
stop-blogs:
	docker stop blog1 blog2

## Remove a Containers
rm-blogs:
	docker rm -f blog1 blog2 

## Run Wordpress/MySQL (with Linked Containers)
run-wp:
	docker run --name wp-mysql -e MYSQL_ROOT_PASSWORD=welcome -dt mysql
	docker run --name wordpress --link wp-mysql:mysql -p 10003:80 -dt wordpress

## Connect to Wordpress Shell
shell-wp:
	docker exec -it wordpress bash 

## Connect to Mysql Shell
shell-mysql:
	docker exec -it wp-mysql bash 

## Stop Containers
stop-wp:
	docker stop wordpress wp-mysql

## Remove a Containers
rm-wp:
	docker rm -f wordpress wp-mysql

## Show all Containers
ps:
	docker ps -a

## Stop All Containers
stop-all:
	docker stop $(docker ps -a -q)
	docker rm -f $(docker ps -a -q)	

## Remove All Images
rmi-all:
	docker rmi -f $(docker images -q)


# References

    https://www.manning.com/books/docker-in-practice
    



                                                             

    