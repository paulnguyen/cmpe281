all: clean 

clean:
	docker stop mysql
	docker rm mysql
	docker stop tomcat
	docker rm tomcat

network-create:
	docker network create --driver bridge gumball

network-inspect:
	docker network inspect gumball

network-inspect-host:
	docker network inspect host

network-ls:
	docker network ls

mysql-run:
	docker run -d --name mysql --network gumball -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe281 mysql:5.5

mysql-shell:
	docker exec -it mysql bash 

tomcat-run:
	docker run -d --name tomcat -v /tmp:/tmp --network gumball -td -p 8080:8080 tomcat:7.0.92 

tomcat-shell:
	docker exec -it tomcat bash 

tomcat-commit:
	docker commit $(container) paulnguyen/gumball-tomcat:7.0.92

tomcat-push:
	docker push paulnguyen/gumball-tomcat:7.0.92

mysql-commit:
	docker commit $(container) paulnguyen/gumball-mysql:5.5

mysql-push:
	docker push paulnguyen/gumball-mysql:5.5

tomcat-run2:
	docker run -d --name tomcat -v /tmp:/tmp --network gumball -td -p 8080:8080 paulnguyen/gumball-tomcat:7.0.92 

mysql-run2:
	docker run -d --name mysql --network gumball -td -p 3306:3306  paulnguyen/gumball-mysql:5.5

docker-login:
	docker login -u paulnguyen

docker-clean: clean
	docker rmi mysql
	docker rmi tomcat

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

compose-up:
	docker-compose up 

compose-down:
	docker-compose down 	

mysql-up:
	docker-compose up -d mysql

tomcat-up:
	docker-compose scale tomcat=2 

mysql-compose-shell:
	docker exec -it gumball_mysql_1 bash

tomcat-compose-shell-1:
	docker exec -it gumball_tomcat_1 bash

tomcat-compose-shell-2:
	docker exec -it gumball_tomcat_2 bash



