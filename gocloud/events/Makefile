
all: clean

clean: 
	find . -name 'main' -type f -exec rm -f {} \; 
	go clean

docker-ip:
	docker-machine ip

rabbitmq-run:
	docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672-p 4369:4369 -p 5672:5672 rabbitmq:3-management

rabbitmq-delete:
	docker stop some-rabbit
	docker rm some-rabbit
	docker rmi some-rabbit	

rabbitmq-send:
	go run src/app/send.go

rabbitmq-receive:
	go run src/app/receive.go	


