

# Docker - Go Gumball API (Deployment Options)

* https://github.com/paulnguyen/cmpe281/tree/master/docker/gumball_makefile
* https://github.com/paulnguyen/cmpe281/tree/master/docker/gumball_compose
* https://github.com/paulnguyen/cmpe281/tree/master/docker/gumball_dockercloud

## Docker Build CLI

* https://docs.docker.com/edge/engine/reference/commandline/docker/
* https://docs.docker.com/edge/engine/reference/commandline/build/
* https://docs.docker.com/edge/engine/reference/commandline/run/

## Docker Compose

* https://docs.docker.com/compose/overview/

## Docker Cloud Stack

* https://docs.docker.com/docker-cloud/apps/stacks/

## Install Go

* https://golang.org/doc/install

## Take a Tour of Go

* https://tour.golang.org/list

## MongoDB

* https://docs.mongodb.com/manual/mongo/

## RabbitmQ

* https://www.rabbitmq.com/management-cli.html


# Go Gumball Sample Code

## Go Data Types

```
package main

type gumballMachine struct {
	Id             	int 	
	CountGumballs   int    	
	ModelNumber 	string	    
	SerialNumber 	string	
}

type order struct {
	Id             	string 	
	OrderStatus 	string	
}

var orders map[string] order
```

## Go Main Entry Point

```
package main

import (
	"os"
)

func main() {

	port := os.Getenv("PORT")
	if len(port) == 0 {
		port = "3000"
	}

	server := NewServer()
	server.Run(":" + port)
}
```

## Go API Server

```
// MongoDB Config
var mongodb_server = "mongodb"
var mongodb_database = "cmpe281"
var mongodb_collection = "gumball"

// RabbitMQ Config
var rabbitmq_server = "rabbitmq"
var rabbitmq_port = "5672"
var rabbitmq_queue = "gumball"
var rabbitmq_user = "guest"
var rabbitmq_pass = "guest"

// NewServer configures and returns a Server.
func NewServer() *negroni.Negroni {
	formatter := render.New(render.Options{
		IndentJSON: true,
	})
	n := negroni.Classic()
	mx := mux.NewRouter()
	initRoutes(mx, formatter)
	n.UseHandler(mx)
	return n
}

// API Routes
func initRoutes(mx *mux.Router, formatter *render.Render) {
	mx.HandleFunc("/ping", pingHandler(formatter)).Methods("GET")
	mx.HandleFunc("/gumball", gumballHandler(formatter)).Methods("GET")
	mx.HandleFunc("/gumball", gumballUpdateHandler(formatter)).Methods("PUT")
	mx.HandleFunc("/order", gumballNewOrderHandler(formatter)).Methods("POST")
	mx.HandleFunc("/order/{id}", gumballOrderStatusHandler(formatter)).Methods("GET")
	mx.HandleFunc("/order", gumballOrderStatusHandler(formatter)).Methods("GET")
	mx.HandleFunc("/orders", gumballProcessOrdersHandler(formatter)).Methods("POST")
}
```

## Go API Backend Setup

```

/*

-- RabbitMQ Setup
-- Default User/Pass: guest/guest

	http://localhost:8080

-- RabbitMQ Create Queue:  

	Queue Name: gumball
	Durable:	no

-- Gumball MongoDB Create Database

	Database Name: cmpe281
	Collection Name: gumball

-- Gumball MongoDB Collection (Create Document)

	use cmpe281
	show dbs

	db.gumball.insert(
	    { 
	      Id: 1,
	      CountGumballs: NumberInt(202),
	      ModelNumber: 'M102988',
	      SerialNumber: '1234998871109' 
	    }
	) ;

-- Gumball MongoDB Collection - Find Gumball Document

	db.gumball.find( { Id: 1 } ) ;

	{
	    "_id" : ObjectId("54741c01fa0bd1f1cdf71312"),
	    "Id" : 1,
	    "CountGumballs" : 202,
	    "ModelNumber" : "M102988",
	    "SerialNumber" : "1234998871109"
	}

-- Gumball MongoDB Collection - Update Gumball Document

	db.gumball.update( 
	    { Id: 1 }, 
	    { $set : { CountGumballs : NumberInt(10) } },
	    { multi : false } 
	)

-- Gumball Delete Documents

	db.gumball.remove({})

 ```

# Go Gumball API Makefile

* https://github.com/paulnguyen/cmpe281/tree/master/docker/gumball_makefile

```
build:
	go build gumball

start:
	./gumball 

docker-build: 
	docker build -t gumball .
	docker images

rabbitmq-run:
	docker run --name rabbitmq \
			   -p 8080:15672 -p 4369:4369 -p 5672:5672 \
			   -d rabbitmq:3-management
mongodb-run:
	docker run --name mongodb -p 27017:27017 -d mongo:3.7

docker-run:
	docker run \
	  		--link mongodb:mongodb \
            --link rabbitmq:rabbitmq \
			--name gumball -p 3000:3000 -td gumball
	docker ps

docker-network:
	docker network ls

docker-network-inspect:
	docker network inspect host

docker-shell:
	docker exec -it gumball bash 

docker-clean:
	docker stop mongodb
	docker stop rabbitmq
	docker rm mongodb
	docker rm rabbitmq
	docker stop gumball
	docker rm gumball
	docker rmi gumball

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

test-ping:
	curl localhost:3000/ping

test-get-inventory:
	curl localhost:3000/gumball

test-update-inventory:
	curl -X PUT \
  	http://localhost:3000/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://localhost:3000/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://localhost:3000/order \
  	-H 'Content-Type: application/json'

test-process-order:
	curl -X POST \
  	http://localhost:3000/orders \
  	-H 'Content-Type: application/json'
```


# Running Go API on Localhost with Backing Services in Docker (Using Docker Run)

* https://github.com/paulnguyen/cmpe281/tree/master/docker/gumball_makefile

## Startup Backing Services

```
make mongodb-run
make rabbitmq-run
make docker-ps-ports
```

## Setup RabbitMQ Queue for Gumball API

```
-- RabbitMQ Setup
-- Default User/Pass: guest/guest

	http://localhost:8080

-- RabbitMQ Create Queue:  

	Queue Name: gumball
	Durable:	no
```

## Setup MongoDB Database

```
-- Gumball MongoDB Create Database

	Database Name: cmpe281
	Collection Name: gumball

-- Gumball MongoDB Collection (Create Document)

	use cmpe281
	show dbs

	db.gumball.insert(
	    { 
	      Id: 1,
	      CountGumballs: NumberInt(202),
	      ModelNumber: 'M102988',
	      SerialNumber: '1234998871109' 
	    }
	) ;

-- Gumball MongoDB Collection - Find Gumball Document

	db.gumball.find( { Id: 1 } ) ;

```

## Map "rabbitmq" and "mongodb" to localhhost

```
# I.E. /etc/hosts (on unix)
# Custom Entries for Development
127.0.0.1       mongodb
127.0.0.1       rabbitmq
```

## Build and Run Gumball API

```
# Note: make sure GOPATH is set
make build
make start
```

## Test Gumball API

```
test-ping:
	curl localhost:3000/ping

test-get-inventory:
	curl localhost:3000/gumball

test-update-inventory:
	curl -X PUT \
  	http://localhost:3000/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://localhost:3000/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://localhost:3000/order \
  	-H 'Content-Type: application/json'

test-process-order:
	curl -X POST \
  	http://localhost:3000/orders \
  	-H 'Content-Type: application/json'
```

## Clean Up

```
make docker-clean
```


# Running Go API and Backing Services in Docker (Using Docker Run)

* https://github.com/paulnguyen/cmpe281/tree/master/docker/gumball_compose

## Build and Startup Docker Containers (note: only gumball port is mapped)

```
docker-build: 
	docker build -t gumball .
	docker images

rabbitmq-run:
	docker run --name rabbitmq \
			   -d rabbitmq:3-management
mongodb-run:
	docker run --name mongodb -d mongo:3.7

docker-run:
	docker run \
	  		--link mongodb:mongodb \
            --link rabbitmq:rabbitmq \
			--name gumball -p 9000:3000 -td gumball
	docker ps

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"
```

## Configure RabbitMQ and MongoDB Backing Services

```
-- RabbitMQ Create Queue:  

	Queue Name: gumball
	Durable:	no

	> docker exec -it rabbitmq bash 
	> rabbitmqadmin declare queue name=gumball durable=false
	> rabbitmqadmin list queues vhost name node messages 

-- Gumball MongoDB Create Database

	Database Name: cmpe281
	Collection Name: gumball

-- Gumball MongoDB Collection (Create Document)

	> docker exec -it mongodb bash 
	> mongo
	
	use cmpe281
	show dbs

	db.gumball.insert(
	    { 
	      Id: 1,
	      CountGumballs: NumberInt(202),
	      ModelNumber: 'M102988',
	      SerialNumber: '1234998871109' 
	    }
	) ;

-- Gumball MongoDB Collection - Find Gumball Document

	db.gumball.find( { Id: 1 } ) ;
```

##  Test Gumball API

```
test-ping:
	curl localhost:9000/ping

test-get-inventory:
	curl localhost:9000/gumball

test-update-inventory:
	curl -X PUT \
  	http://localhost:9000/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-process-order:
	curl -X POST \
  	http://localhost:9000/orders \
  	-H 'Content-Type: application/json'
```

## Clean Up

```
make docker-clean
```

# Running Go API and Backing Services in Docker (Using Docker Compose)

* https://github.com/paulnguyen/cmpe281/tree/master/docker/gumball_compose

## Docker Compose Configuration: docker-compose.yml

```
version: "3"
services:
  gumball:
    image: <your account>/gumball:gumball-v2.0
    ports:
      - "9000:3000"
    networks:
      - webnet
  rabbitmq:
    image: rabbitmq:3-management
    networks:
      - webnet
  mongodb:
    image: mongo:3.7
    networks:
      - webnet
networks:
  webnet:
```

## Build Gumball Image and Publish to your Docker Hub Account

* Use: docker.sh

1. login
2. build
3. push

## Startup Docker Containers (note: only gumball port is mapped)

```
up:
	docker-compose up -d

down:
	docker-compose down

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"
```

## Configure RabbitMQ and MongoDB Backing Services

```
-- RabbitMQ Create Queue:  

	Queue Name: gumball
	Durable:	no

	> docker exec -it <name of containeer> bash 
	> rabbitmqadmin declare queue name=gumball durable=false
	> rabbitmqadmin list queues vhost name node messages 

-- Gumball MongoDB Create Database

	Database Name: cmpe281
	Collection Name: gumball

-- Gumball MongoDB Collection (Create Document)

	> docker exec -it <name of container> bash 
	> mongo
	
	use cmpe281
	show dbs

	db.gumball.insert(
	    { 
	      Id: 1,
	      CountGumballs: NumberInt(202),
	      ModelNumber: 'M102988',
	      SerialNumber: '1234998871109' 
	    }
	) ;

-- Gumball MongoDB Collection - Find Gumball Document

	db.gumball.find( { Id: 1 } ) ;
```

##  Test Gumball API

```
test-ping:
	curl localhost:9000/ping

test-get-inventory:
	curl localhost:9000/gumball

test-update-inventory:
	curl -X PUT \
  	http://localhost:9000/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-process-order:
	curl -X POST \
  	http://localhost:9000/orders \
  	-H 'Content-Type: application/json'
```

## Clean Up

```
docker-compose down
```


# Running Go API and Backing Services in Docker Desktop with Kubernetes

* https://docs.docker.com/docker-for-mac/kubernetes/

## Start Gumball Services using Compose Stack

```
docker stack deploy --compose-file docker-compose.yml gumball_stack
docker stack services gumball_stack
```

## Configure RabbitMQ and MongoDB Backing Services

```
-- RabbitMQ Create Queue:  

	Queue Name: gumball
	Durable:	no

	> docker exec -it <name of containeer> bash 
	> rabbitmqadmin declare queue name=gumball durable=false
	> rabbitmqadmin list queues vhost name node messages 

-- Gumball MongoDB Create Database

	Database Name: cmpe281
	Collection Name: gumball

-- Gumball MongoDB Collection (Create Document)

	> docker exec -it <name of container> bash 
	> mongo
	
	use cmpe281
	show dbs

	db.gumball.insert(
	    { 
	      Id: 1,
	      CountGumballs: NumberInt(202),
	      ModelNumber: 'M102988',
	      SerialNumber: '1234998871109' 
	    }
	) ;

-- Gumball MongoDB Collection - Find Gumball Document

	db.gumball.find( { Id: 1 } ) ;
```

##  Test Gumball API

```
test-ping:
	curl localhost:9000/ping

test-get-inventory:
	curl localhost:9000/gumball

test-update-inventory:
	curl -X PUT \
  	http://localhost:9000/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-process-order:
	curl -X POST \
  	http://localhost:9000/orders \
  	-H 'Content-Type: application/json'
```

## Clean Up

```
docker stack rm gumball_stack
```



# *** Docker Cloud Service Has been Deprecated by Docker ***
# Running Go API and Backing Services in Docker Cloud (Using Docker Stack) 

* https://github.com/paulnguyen/cmpe281/tree/master/docker/gumball_dockercloud


## Docker Cloud Stack Configuration: docker-cloud.yml

```
lb:
  image: 'dockercloud/haproxy:latest'
  links:
    - gumball
  ports:
    - '80:80'
  roles:
    - global
gumball:
  image: paulnguyen/gumball
  links:
    - rabbitmq
    - mongodb
  target_num_containers: 2
rabbitmq:
  image: rabbitmq:3-management
mongodb:
  image: mongo:3.7
```

## Build Gumball Image and Publish to your Docker Hub Account

* Use: docker.sh

1. login
2. build
3. push

## Startup Docker Containers in Docker Cloud

![Example](./cloudstack-run.png)

## Configure RabbitMQ and MongoDB Backing Services

```
-- RabbitMQ Create Queue:  

	> docker exec -it <name of containeer> bash 
	> rabbitmqadmin declare queue name=gumball durable=false
	> rabbitmqadmin list queues vhost name node messages 

-- Gumball MongoDB Collection (Create Document)
	
	use cmpe281

	db.gumball.insert(
	    { 
	      Id: 1,
	      CountGumballs: NumberInt(202),
	      ModelNumber: 'M102988',
	      SerialNumber: '1234998871109' 
	    }
	) ;

-- Gumball MongoDB Collection - Find Gumball Document

	db.gumball.find( { Id: 1 } ) ;
```

##  Test Gumball API

```
test-ping:
	curl dockerhost/ping

test-get-inventory:
	curl dockerhost/gumball

test-update-inventory:
	curl -X PUT \
  	http://dockerhost/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://dockerhost/order \
  	-H 'Content-Type: application/json'

test-place-order:
	curl -X POST \
  	http://dockerhost/order \
  	-H 'Content-Type: application/json'

test-place-order:
	curl -X POST \
  	http://dockerhost/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://dockerhost/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://dockerhost/order \
  	-H 'Content-Type: application/json'
  	
test-process-order:
	curl -X POST \
  	http://dockerhost/orders \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://dockerhost/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://dockerhost/order \
  	-H 'Content-Type: application/json'  	
```

## Clean Up

```
Terminate your Cloud Stack
```


