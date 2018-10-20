
all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;
	rm -rf build/*

compile:
	gradle build

jar: compile
	gradle shadowJar

run-api-server: 
	echo Starting API Service at:  http://localhost:9090
	java -cp build/libs/starbucks-all.jar api.StarbucksServer

run-log-server:
	echo Starting Log Server at localhost port 8888
	java -cp build/libs/starbucks-all.jar log.LogServer

loadtest: 
	echo Starting Load Test on localhost
	java -cp build/libs/starbucks-all.jar:build/classes/test RunLoadTest 5



	
