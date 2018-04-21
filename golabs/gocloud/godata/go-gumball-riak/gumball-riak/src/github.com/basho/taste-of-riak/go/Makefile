.PHONY: all install-deps lint run run01 run02 run03

PROJDIR = $(realpath $(CURDIR))

all: install-deps lint run

install-deps:
	go get -t ./...

lint: install-deps
	go tool vet -shadow=true -shadowstrict=true $(PROJDIR)
	go vet github.com/basho/taste-of-riak/go/...

fmt:
	gofmt -s -w $(PROJDIR)

run: lint run01 run02 run03

run01:
	go run ./ch01/ch01.go

run02:
	go run ./ch02/ch02.go

run03:
	go run ./ch03/ch03.go

