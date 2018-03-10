
# https://tour.golang.org/welcome

all: clean

clean: 
	find . -name 'main' -type f -exec rm -f {} \; 
	go clean

build:
	go build main

run:
	go run src/main/hello.go

playground:
	go run src/playground/main.go

format:
	go fmt main

install:
	go install main

echo1:
	go run src/main/echo1.go arg1 arg2 arg3 arg4

echo2:
	go run src/main/echo2.go arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10

echo3:
	go run src/main/echo3.go arg1 arg2 arg3 arg4 arg5 arg6 arg7 arg8 arg9 arg10

echo4:
	go run src/main/echo4.go arg1 arg2 arg3 arg4

dup1:
	go run src/main/dup1.go 

dup2:
	go run src/main/dup2.go src/main/file.txt

dup3:
	go run src/main/dup3.go src/main/file.txt
