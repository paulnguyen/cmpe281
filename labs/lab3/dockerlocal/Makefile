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

network-cleanup:
	docker network prune

mysql-run:
	docker run -d --name mysql --network gumball -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe281 mysql:5.5

mysql-shell:
	docker exec -it mysql bash 

gumball-run:
	docker run -d --name gumball --network gumball -td -p 8080:8080 gumball 

gumball-shell:
	docker exec -it gumball bash 

gumball-commit:
	docker commit $(container) paulnguyen/grails-gumball:latest

gumball-push:
	docker push paulnguyen/grails-gumball:latest

mysql-commit:
	docker commit $(container) paulnguyen/gumball-mysql:5.5

mysql-push:
	docker push paulnguyen/gumball-mysql:5.5

gumball-run-custom-image:
	docker run -d --name tomcat -v /tmp:/tmp --network gumball -td -p 8080:8080 paulnguyen/grails-gumball:latest

mysql-run-custom-image:
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

gumball-up-2:
	docker-compose scale gumball=2 

gumball-up-4:
	docker-compose scale gumball=4 

lb-up:
	docker-compose up -d lb

lb-config:
	docker exec dockerlocal_lb_1 cat haproxy.cfg

mysql-compose-shell:
	docker exec -it dockerlocal_mysql_1 bash

gumball-compose-shell-1:
	docker exec -it dockerlocal_gumball_1 bash

gumball-compose-shell-2:
	docker exec -it dockerlocal_gumball_2 bash

gumball-logs-1:
	docker logs dockerlocal_gumball_1

gumball-logs-2:
	docker logs dockerlocal_gumball_2

gumball-logs-3:
	docker logs dockerlocal_gumball_3

gumball-logs-4:
	docker logs dockerlocal_gumball_4



