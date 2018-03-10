
all: clean

clean: 
	find . -name 'app' -type f -exec rm -f {} \; 
	rm -rf bin/*
	go clean

build:
	go build src/app/$(app).go
	mv $(app) bin

run:
	go run src/app/$(app).go

main:
	go run src/app/main.go

format:
	go fmt app
