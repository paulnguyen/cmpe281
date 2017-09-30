
# Cloud Native Go | Go API Example


## SSH Into Docker Machine (Mac)

    docker-machine ip
    ssh docker@<host ip>
    Password: tcuser

## Docker Images Needed

    docker pull golang:latest 
    docker pull mongo:latest
    docker pull rabbitmq:3-management
    docker pull kong:latest
    docker pull cassandra:2.2

## Build and Run Go API App

    Docker Build
    ============

        docker build -t goapi .
        docker images

    Docker Run
    ==========

        docker run --name goapi -td --net=host goapi
        docker ps

    Dockerfile
    ==========

        FROM golang:latest 
        EXPOSE 8080
        RUN mkdir /app 
        ADD . /app/ 
        WORKDIR /app 
        ENV GOPATH /app
        RUN cd /app ; go build github.com/cloudnativego/hello
        CMD ["/app/hello"]

## Run RabbitMQ Container

    Installing RabbitMQ
    ===================

        https://hub.docker.com/_/rabbitmq/

        docker run -d --name rabbitmq -p 8080:15672 -p 4369:4369 -p 5672:5672 rabbitmq:3-management
        docker stop rabbitmq
        docker rm rabbitmq
        docker ps --all
        docker start rabbitmq

        //--hostname my-rabbit

        https://www.rabbitmq.com/management.html
        https://www.rabbitmq.com/networking.html

        Admin Console Runs on Port: 8080            (for administrative console - default user/pass: guest/guest)
        RabbitMQ Default Listening Port: 5672       (for application clients)
        RabbitMQ EPMD Port: 4369                    (for internal node communications)


    RabbitMQ Hello Example
    ======================
    
        https://github.com/cloudnativego/rabbit-hello
        
        go get github.com/streadway/amqp

        https://github.com/cloudnativego/rabbit-hello/blob/master/receive.go
        https://github.com/cloudnativego/rabbit-hello/blob/master/send.go
	

## Run Kong API Gateway

    docker run -d --name kong-database -p 9042:9042 cassandra:2.2
    docker run -d --name kong \
              --link kong-database:kong-database \
              -e "KONG_DATABASE=cassandra" \
              -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
              -e "KONG_PG_HOST=kong-database" \
              -p 8000:8000 \
              -p 8443:8443 \
              -p 8001:8001 \
              -p 7946:7946 \
              -p 7946:7946/udp \
              kong


## Run MongoDB Container

    https://hub.docker.com/_/mongo/

    docker run --name mongodb -p 27017:27017 -d mongo
    docker stop mongodb
    docker rm mongodb
    docker ps --all
    docker start mongodb

    This image includes EXPOSE 27017 (the mongo port), so standard container 
    linking will make it automatically available to the linked containers.


    -- Gumball MongoDB Collection (Create Document) --

    db.gumball.insert(
    { 
      Id: 1,
      CountGumballs: 202,
      ModelNumber: 'M102988',
      SerialNumber: '1234998871109' 
    }
    ) ;

    -- Gumball MongoDB Collection - Find Gumball Document --

    db.gumball.find( { Id: 1 } ) ;

    -- Gumball Machine Inventory --

    db.gumball.find()

    {
        "_id" : ObjectId("54741c01fa0bd1f1cdf71312"),
        "id" : 1,
        "countGumballs" : 8,
        "modelNumber" : "M102988",
        "serialNumber" : "1234998871109"
    }

    db.gumball.update( 
        { id: 1 }, 
        { $set : { countGumballs : 10 } },
        { multi : false } 
    )

    -- Gumball Orders --

    db.gumballorders.insert( { OrdNum: '6447451112210432', OrdStatus: 'Submitted', } )

    db.gumballorders.insert( 
        {   OrdNum: '6447451112210432',
            OrdStatus: 'Submitted',
        }
    )

    db.gumballorders.find(
        {
            "_id" : ObjectId("55439cea302ed2c804b0fe10")
        }
    )

    db.gumballorders.find(
        {
            "OrdNum" : "6737166575075328"
        }
    )

    db.gumballorders.find()

    {
        "_id" : ObjectId("547d6512365294180dd1d365"),
        "OrdNum" : "5222465777172480",
        "OrdStatus" : "Backorder",
        "__v" : 0
    }

    {
        "_id" : ObjectId("547d65544fc50d1b0d7c864a"),
        "OrdNum" : "5843778627698688",
        "OrdStatus" : "Backorder",
        "__v" : 0
    }

    {
        "_id" : ObjectId("547d6a3c98a4a4240ddc0f11"),
        "OrdNum" : "7209957814435840",
        "OrdStatus" : "Submitted",
        "__v" : 0
    }

    {
        "_id" : ObjectId("547d6a4198a4a4240ddc0f12"),
        "OrdNum" : "5343800146788352",
        "OrdStatus" : "Submitted",
        "__v" : 0
    }


    -- Stored MongoDB JavaScript Function --
    -- http://docs.mongodb.org/manual/tutorial/store-javascript-function-on-server/ --


    function processGumballOrders() {
        gm = db.gumball.find( { id : 1 } ) ;
        cnt = gm.next().countGumballs ;
        print( "Current Inventory: " + cnt ) ;
        cursor = db.gumballorders.find( { OrdStatus: { $ne: "Shipped" } } );
        cursor.snapshot() ; // force read isolation
        while ( cursor.hasNext() ) {
            order = cursor.next() ;        
            printjson( order );
            if ( cnt > 0 ) {
                cnt-- ;
                db.gumballorders.update( 
                    { _id: order._id }, 
                    { $set : { OrdStatus : "Shipped" } },
                    { multi : false } 
                );
            }
            else {
                db.gumballorders.update( 
                    { _id: order._id }, 
                    { $set : { OrdStatus : "Backorder" } },
                    { multi : false } 
                );
            }
        }   
        db.gumball.update ( 
            { id: 1 }, 
            { $set : { countGumballs : cnt } },
            { multi : false } 
        )
    }

    processGumballOrders() ;



## Dockertize Node.js App

    https://nodejs.org/en/docs/guides/nodejs-docker-webapp/


## Docker Container / Go Lab

1. Make sure these images are downloaded before exam to save time:

    docker pull golang:latest
    docker pull mongo:latest
    docker pull rabbitmq:3-management
    docker pull kong:latest
    docker pull cassandra:2.2

2. Node.js (ver 5.2) and Go API (ver 2) will be used.  There may be slight issues
   due to the version of Node.js and Go you are using.  If so, you are to adapt the
   code accordingly.  Versions tested by Instructor:  Node.js v6.10.3 & Go 1.7.4.

3. You are to make minor modifications to the code and then deploy into Docker.  These
   modifications will be in the configuration / connections made between Go API and backend Services
   and Node.js App and Kong API Gateway.

4. You will then demonstrate successful deployment by answering questions and submitting screenshots.
   Particular attention to Docker Deployment Networking and Exposed Ports will be a major factor in 
   grading.  Make sure to only open/export ports that are necessary.

5. Docker Containers:

    Go Code will be an API deployed to a Docker Container using golang:latest as Base Image
    MongoDB will be deployed to a Docker Container
    RabbitMQ will be deployed to a Docker Container
    Kong and Cassandra will be deployed to Docker Containers

6. Container Configurations:

    Node.js App will be Frontend (running on localhost) connected to Kong API Gateway Docker Container
    Go API Server will connect to RabbitMQ and MongoDB
    Kong API Gateway will connect to Cassandra Container (as in Starbucks Lab v3)
    Kong API Gateway will be configured to forward API calls to Go API Container as Upstream Server


    