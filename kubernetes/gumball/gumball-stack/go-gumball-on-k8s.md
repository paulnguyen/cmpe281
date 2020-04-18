

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


