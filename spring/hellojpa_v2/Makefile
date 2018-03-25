all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;
	rm -rf build/classes/*
	rm -rf build/libs/*

compile:
	gradle build

run:
	gradle bootRun

run-jar:
	java -jar build/libs/hellojpa-2.0.0.jar


