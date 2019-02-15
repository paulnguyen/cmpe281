
all: clean

clean:
	docker stop mongo
	docker rm mongo

mongo-run:
	docker run --name mongo -p 27017:27017 -d mongo

mongo-shell:
	docker exec -it mongo bash 

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"
