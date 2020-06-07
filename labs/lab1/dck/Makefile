
all: docker-clean 

up:
	docker-compose up -d

down:
	docker-compose down

network-prune:
	docker network prune

docker-build: 
	docker build -t phptest .
	docker images

docker-clean:
	docker stop phptest
	docker rm phptest
	docker rmi phptest

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

docker-clean-all:
	docker stop $(docker ps -aq)
	docker rm $(docker ps -aq)
	docker rmi $(docker images -q)

docker-run:
	docker run -dit --name phptest -p 8080:80 phptest
	docker ps

docker-stop:
	docker stop phptest
	docker rm phptest

docker-shell:
	docker exec -it phptest bash 



