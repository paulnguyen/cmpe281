
all: clean

clean: 
	find . -name 'app' -type f -exec rm -f {} \; 
	go clean

build:
	go build app
	mv app bin

run:
	./bin/app repo:golang/go is:open json decoder

format:
	go fmt app

install:
	go install app

