
all: clean

clean: 
	find . -name 'hello' -type f -exec rm -f {} \;
	go clean

get:
	go get github.com/cloudnativego/hello

format:
	go fmt github.com/cloudnativego/hello

build:
	go build github.com/cloudnativego/hello

run-src:
	go run src/github.com/cloudnativego/hello/main.go

run-here:
	./hello

run-bin:
	./bin/hello

test:
	curl http://localhost:8080

install:
	go install github.com/cloudnativego/hello


