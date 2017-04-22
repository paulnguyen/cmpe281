
all: clean

clean: 
	find . -name 'app' -type f -exec rm -f {} \; 
	go clean

build:
	go build app
	mv app bin

run:
	go run src/app/$(app).go

format:
	go fmt app

install:
	go install app


