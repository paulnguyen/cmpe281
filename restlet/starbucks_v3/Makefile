
all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;
	rm -rf build/*

compile:
	gradle build

jar: compile
	gradle shadowJar

run: 
	echo Starting Service at:  http://localhost:9090
	java -cp build/libs/starbucks-all.jar api.StarbucksServer

loadtest: 
	echo Starting Load Test on localhost
	java -cp build/libs/starbucks-all.jar:build/classes/test RunLoadTest 5

docker-build: 
	docker build -t starbucks .
	docker images

docker-clean:
	docker stop starbucks
	docker rm starbucks
	docker rmi starbucks

docker-run:
	docker run --name starbucks -td starbucks
	docker ps

docker-run-host:
	docker run --name starbucks -td --net=host starbucks
	docker ps

docker-run-bridge:
	docker run --name starbucks -td -p 90:9090 starbucks
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop starbucks
	docker rm starbucks

docker-shell:
	docker exec -it starbucks bash 

run-app:
	docker run -d --name starbucks -td starbucks
	docker run -d --name kong-database -p 9042:9042 cassandra:2.2
	docker run -d --name kong \
			  --link starbucks:starbucks \
              --link kong-database:kong-database \
              -e "KONG_DATABASE=cassandra" \
              -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
              -e "KONG_PG_HOST=kong-database" \
              -p 8000:8000 \
              -p 8443:8443 \
              -p 8001:8001 \
              -p 7946:7946 \
              -p 7946:7946/udp \
              kong



	
