

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
