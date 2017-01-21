all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;
	rm xmlrpc.war

compile: 
	javac -cp ./lib/servlet-api.jar:./lib/xalan.jar:./lib/xerces.jar \
	-d .//build/WEB-INF/classes \
	./source/edu/sjsu/cmpe281/*.java \
	./source/helma/xmlrpc/*.java
	
war:
	genwar.sh
	
docker: compile war
	docker build -t xmlrpc .
	docker run --name xmlrpc -td --net=host xmlrpc
	docker exec -it xmlrpc bash 	

docker-build: 
	docker build -t xmlrpc .
	docker images

docker-clean:
	docker stop xmlrpc
	docker rm xmlrpc
	docker rmi xmlrpc

docker-run:
	docker run --name xmlrpc -td xmlrpc
	docker ps

docker-run-host:
	docker run --name xmlrpc -td --net=host xmlrpc
	docker ps

docker-run-bridge:
	docker run --name xmlrpc -td -p 80:8080 xmlrpc
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop xmlrpc
	docker rm xmlrpc

docker-shell:
	docker exec -it xmlrpc bash 

