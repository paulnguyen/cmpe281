
all: clean

clean:
	docker stop mysql
	docker rm mysql

mysql-run:
	docker run -d --name mysql --network gumball -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe281 mysql:5.7

mysql-shell:
	docker exec -it mysql bash 

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

