
all: clean 

clean:
	rm -rf build/*
	rm -f dist/app.jar
	find . -name "*.class" -exec rm -rf {} \;

jar: compile
	cd build ; jar -cvfe ../dist/app.jar GumballServer .

test: compile app
	java -cp \
	dist/org.restlet-2.3.7.jar:\
	dist/org.restlet.ext.json-2.3.7.jar:\
	dist/json.jar:\
	dist/app.jar api.GumballServer

compile: 
	javac -cp \
	dist/org.restlet-2.3.7.jar:\
	dist/org.restlet.ext.json-2.3.7.jar:\
	dist/org.restlet.ext.jackson-2.3.7.jar:\
	dist/jackson-core-2.8.3.jar:\
	dist/jackson-annotations-2.8.3.jar:\
	dist/jackson-databind-2.8.3.jar:\
	dist/jackson-dataformat-csv-2.8.3.jar:\
	dist/jackson-dataformat-smile-2.8.3.jar:\
	dist/jackson-dataformat-xml-2.8.3.jar:\
	dist/jackson-dataformat-yaml-2.8.3.jar:\
	dist/json.jar -d build \
	src/gumball/*.java \
	clients/loadtestclient/*.java \
	src/api/*.java

run: compile
	echo Starting Service at:  http://localhost:8080
	java -cp \
	dist/json.jar:\
	dist/org.restlet-2.3.7.jar:\
	dist/org.restlet.ext.json-2.3.7.jar:\
	dist/org.restlet.ext.jackson-2.3.7.jar:\
	dist/jackson-core-2.8.3.jar:\
	dist/jackson-annotations-2.8.3.jar:\
	dist/jackson-databind-2.8.3.jar:\
	dist/jackson-dataformat-csv-2.8.3.jar:\
	dist/jackson-dataformat-smile-2.8.3.jar:\
	dist/jackson-dataformat-xml-2.8.3.jar:\
	dist/jackson-dataformat-yaml-2.8.3.jar:\
	build api.GumballServer


loadtest: compile
	echo Starting Load Test on localhost
	java -cp \
	dist/json.jar:\
	dist/org.restlet-2.3.7.jar:\
	dist/org.restlet.ext.json-2.3.7.jar:\
	dist/org.restlet.ext.jackson-2.3.7.jar:\
	dist/jackson-core-2.8.3.jar:\
	dist/jackson-annotations-2.8.3.jar:\
	dist/jackson-databind-2.8.3.jar:\
	dist/jackson-dataformat-csv-2.8.3.jar:\
	dist/jackson-dataformat-smile-2.8.3.jar:\
	dist/jackson-dataformat-xml-2.8.3.jar:\
	dist/jackson-dataformat-yaml-2.8.3.jar:\
	build RunLoadTest 5

docker-build: app
	docker build -t gumball .
	docker images

docker-clean:
	docker stop gumball
	docker rm gumball
	docker rmi gumball

docker-run:
	docker run --name gumball -td gumball
	docker ps

docker-run-host:
	docker run --name gumball -td --net=host gumball
	docker ps

docker-run-bridge:
	docker run --name gumball -td -p 80:8080 gumball
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop gumball
	docker rm gumball

docker-shell:
	docker exec -it gumball bash 
	
