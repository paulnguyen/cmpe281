all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;

compile:
	gradle build

run:
	gradle bootRun

run-jar:
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/quickpoll-1.0.0.jar

h2:
	echo "http://localhost:8080/h2-console/"
	echo "jdbc:h2:mem:testdb"

test1:
	curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{ \
    "question": "Who will win SuperBowl this year?", \
    "options": [ \
                       {"value": "New England Patriots"}, \
                       {"value": "Seattle Seahawks"}, \
                       {"value": "Green Bay Packers"}, \
                       {"value": "Denver Broncos"}] \
	}' "http://localhost:8080/polls"

test2:
	curl http://localhost:8080/polls

test3:
	curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{ \
	"option" : { "id" : 1, "value" : "New England Patriots" } \
	}' "http://localhost:8080/polls/1/votes"

test4:
	curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache" "http://localhost:8080/computeresult?pollId=1"


docker:
	docker build -t quickpoll .
	docker run --name quickpoll -td --net=host quickpoll
	docker exec -it quickpoll bash 	

docker-build: 
	docker build -t quickpoll .
	docker images

docker-clean:
	docker stop quickpoll
	docker rm quickpoll
	docker rmi quickpoll

docker-run:
	docker run --name quickpoll -td quickpoll
	docker ps

docker-run-host:
	docker run --name quickpoll -td --net=host quickpoll
	docker ps

docker-run-bridge:
	docker run --name quickpoll -td -p 80:8080 quickpoll
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop quickpoll
	docker rm quickpoll

docker-shell:
	docker exec -it quickpoll bash 