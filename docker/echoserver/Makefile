

## Run an Echo Server in Ubuntu Image with Restart Always
run-restart:
	docker run -d --restart=always ubuntu echo done

## Run with Restart on Failure (non-zero return) Max 5 Times
run-restart-5:
	docker run -d --restart=on-failure:5 ubuntu /bin/false

## SHow all Containers
ps:
	docker ps -a

## Remove a Container by Container's ID
rm:
	read -p "Enter Container ID: " id ; docker stop $$id ; docker rm $$id; 
