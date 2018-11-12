/*
	Gumball API in Go
	Uses Mongo & Riak KV
*/

package main

import (
	"fmt"
	"log"
	"net/http"
	"io/ioutil"
	"time"
	"os"
	"strings"
	"encoding/json"
	"github.com/codegangsta/negroni"
	"github.com/gorilla/mux"
	"github.com/unrolled/render"
	"github.com/satori/go.uuid"
	"gopkg.in/mgo.v2"
    "gopkg.in/mgo.v2/bson"
)

/*
	Go Rest Client:
		Tutorial:	https://medium.com/@marcus.olsson/writing-a-go-client-for-your-restful-api-c193a2f4998c
		Reference:	https://golang.org/pkg/net/http/

		var server1 = "http://localhost:7000"
		var server2 = "http://localhost:7001"
		var server3 = "http://localhost:7002"

		var server1 = "http://gumball_member_1:8098"
		var server2 = "http://gumball_member_2:8098"
		var server3 = "http://gumball_member_3:8098"

	Set Localhost Environment:
	
		export MONGO_SERVER="localhost"
		export MONGO_DB="cmpe281"
		export MONGO_COL="gumball"

		export RIAK1="http://localhost:7000"
		export RIAK2="http://localhost:7001"
		export RIAK3="http://localhost:7002"

*/

/* Mongo Config */
var mongodb_server 		= "" // set in environment
var mongodb_database 	= "" // set in environment
var mongodb_collection 	= "" // set in environment

/* Riak REST Client */
var debug = true
var server1 = "" // set in environment
var server2 = "" // set in environment
var server3 = "" // set in environment

type Client struct {
	Endpoint string
	*http.Client
}

var tr = &http.Transport{
	MaxIdleConns:       10,
	IdleConnTimeout:    30 * time.Second,
	DisableCompression: true,
}

func NewClient(server string) *Client {
	return &Client{
		Endpoint:  	server,
		Client: 	&http.Client{Transport: tr},
	}
}

func (c *Client) Ping() (string, error) {
	resp, err := c.Get(c.Endpoint + "/ping" )
	if err != nil {
		fmt.Println("[RIAK DEBUG] " + err.Error())
		return "Ping Error!", err
	}
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if debug { fmt.Println("[RIAK DEBUG] GET: " + c.Endpoint + "/ping => " + string(body)) }
	return string(body), nil
}

func (c *Client) CreateOrder(key, value string) (order, error) {
   	var ord_nil = order {}
	reqbody := "{\"Id\": \"" + 
		key + 
		"\",\"OrderStatus\": \"" +
		 value + 
		 "\"}"
	resp, err := c.Post(c.Endpoint + "/buckets/orders/keys/"+key+"?returnbody=true", 
						"application/json", strings.NewReader(reqbody) )
	if err != nil {
		fmt.Println("[RIAK DEBUG] " + err.Error())
		return ord_nil, err
	}
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if debug { fmt.Println("[RIAK DEBUG] POST: " + c.Endpoint + "/buckets/orders/keys/"+key+"?returnbody=true => " + string(body)) }
	var ord = order {
		Id: key,            		
		OrderStatus: value,
	}
	return ord, nil
}

func (c *Client) GetOrder(key string) (order, error) {
	var ord_nil = order {}
	resp, err := c.Get(c.Endpoint + "/buckets/orders/keys/"+key )
	if err != nil {
		fmt.Println("[RIAK DEBUG] " + err.Error())
		return ord_nil, err
	}
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if debug { fmt.Println("[RIAK DEBUG] GET: " + c.Endpoint + "/buckets/orders/keys/"+key +" => " + string(body)) }
	var ord = order { }
	if err := json.Unmarshal(body, &ord); err != nil {
		fmt.Println("RIAK DEBUG] JSON unmarshaling failed: %s", err)
		return ord_nil, err
	}
	return ord, nil
}

func (c *Client) UpdateOrder(key, value string) (order, error) {
	var ord_nil = order {}
	reqbody := "{\"Id\": \"" + 
		key + 
		"\",\"OrderStatus\": \"" +
		 value + 
		 "\"}"
	req, _  := http.NewRequest("PUT", c.Endpoint + "/buckets/orders/keys/"+key+"?returnbody=true", strings.NewReader(reqbody) )
	req.Header.Add("Content-Type", "application/json")
	//fmt.Println( req )
	resp, err := c.Do(req)	
	if err != nil {
		fmt.Println("[RIAK DEBUG] " + err.Error())
		return ord_nil, err
	}	
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if debug { fmt.Println("[RIAK DEBUG] PUT: " + c.Endpoint + "/buckets/orders/keys/"+key+"?returnbody=true => " + string(body)) }
	var ord = order { }
	if err := json.Unmarshal(body, &ord); err != nil {
		fmt.Println("RIAK DEBUG] JSON unmarshaling failed: %s", err)
		return ord_nil, err
	}
	return ord, nil
}


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

// Init Database Connections

func init() {

	// Get Environment Config
	mongodb_server 		= os.Getenv("MONGO_SERVER") 
	mongodb_database 	= os.Getenv("MONGO_DB")
	mongodb_collection 	= os.Getenv("MONGO_COL")
	server1 = os.Getenv("RIAK1")
	server2 = os.Getenv("RIAK2")
	server3 = os.Getenv("RIAK3")

	fmt.Println("Mongo Server:", mongodb_server )	
	fmt.Println("Riak Server1:", server1 )	
	fmt.Println("Riak Server2:", server2 )	
	fmt.Println("Riak Server3:", server3 )	

	// Mongo Ping Test
	session, err := mgo.Dial(mongodb_server)
    if err != nil {
            panic(err)
    }
    defer session.Close()
    session.SetMode(mgo.Monotonic, true)
    c := session.DB(mongodb_database).C(mongodb_collection)
    var result bson.M
    err = c.Find(bson.M{"SerialNumber" : "1234998871109"}).One(&result)
    if err != nil {
            log.Fatal(err)
    }
    fmt.Println("Gumball Machine:", result )	

	// Riak KV Setup	
	c1 := NewClient(server1)
	msg, err := c1.Ping( )
	if err != nil {
		log.Fatal(err)
	} else {
		log.Println("Riak Ping Server1: ", msg)		
	}

	c2 := NewClient(server2)
	msg, err = c2.Ping( )
	if err != nil {
		log.Fatal(err)
	} else {
		log.Println("Riak Ping Server2: ", msg)		
	}

	c3 := NewClient(server3)
	msg, err = c3.Ping( )
	if err != nil {
		log.Fatal(err)
	} else {
		log.Println("Riak Ping Server3: ", msg)		
	}

}

// API Routes
func initRoutes(mx *mux.Router, formatter *render.Render) {
	mx.HandleFunc("/ping", pingHandler(formatter)).Methods("GET")
	mx.HandleFunc("/gumball", gumballHandler(formatter)).Methods("GET")
	mx.HandleFunc("/gumball", gumballUpdateHandler(formatter)).Methods("PUT")
	mx.HandleFunc("/order", gumballNewOrderHandler(formatter)).Methods("POST")
	mx.HandleFunc("/order/{id}", gumballOrderStatusHandlerAny(formatter)).Methods("GET")
	mx.HandleFunc("/order/{id}", gumballProcessOrdersHandler(formatter)).Methods("POST")
}

// Helper Functions
func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
		panic(fmt.Sprintf("%s: %s", msg, err))
	}
}

// API Ping Handler
func pingHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		formatter.JSON(w, http.StatusOK, struct{ Test string }{"API version 1.0 alive!"})
	}
}

// API Gumball Machine Handler
func gumballHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		session, err := mgo.Dial(mongodb_server)
        if err != nil {
                panic(err)
        }
        defer session.Close()
        session.SetMode(mgo.Monotonic, true)
        c := session.DB(mongodb_database).C(mongodb_collection)
        var result bson.M
        err = c.Find(bson.M{"SerialNumber" : "1234998871109"}).One(&result)
        if err != nil {
                log.Fatal(err)
        }
        fmt.Println("Gumball Machine:", result )
		formatter.JSON(w, http.StatusOK, result)
	}
}

// API Update Gumball Inventory
func gumballUpdateHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
    	var m gumballMachine
    	_ = json.NewDecoder(req.Body).Decode(&m)		
    	fmt.Println("Update Gumball Inventory To: ", m.CountGumballs)
		session, err := mgo.Dial(mongodb_server)
        if err != nil {
                panic(err)
        }
        defer session.Close()
        session.SetMode(mgo.Monotonic, true)
        c := session.DB(mongodb_database).C(mongodb_collection)
        query := bson.M{"SerialNumber" : "1234998871109"}
        change := bson.M{"$set": bson.M{ "CountGumballs" : m.CountGumballs}}
        err = c.Update(query, change)
        if err != nil {
                log.Fatal(err)
        }
       	var result bson.M
        err = c.Find(bson.M{"SerialNumber" : "1234998871109"}).One(&result)
        if err != nil {
                log.Fatal(err)
        }        
        fmt.Println("Gumball Machine:", result )
		formatter.JSON(w, http.StatusOK, result)
	}
}

// API Create New Gumball Order
func gumballNewOrderHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {

		uuid, _ := uuid.NewV4()
		value := "Order Placed"

		c1 := NewClient(server1)
		ord, err := c1.CreateOrder(uuid.String(), value)
		if err != nil {
			log.Fatal(err)
			formatter.JSON(w, http.StatusBadRequest, err)
		} else {
			formatter.JSON(w, http.StatusOK, ord)
		}
	}
}

// API Get Order Status
func gumballOrderStatusHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		
		params := mux.Vars(req)
		var uuid string = params["id"]
		fmt.Println( "Order Params ID: ", uuid )

		if uuid == ""  {
			formatter.JSON(w, http.StatusBadRequest, "Invalid Request. Order ID Missing.")
		} else {
			ord := getOrder(server1, uuid)
			if ord == (order{}) {
				formatter.JSON(w, http.StatusBadRequest, "")
			} else {
				fmt.Println( "Order: ", ord )
				formatter.JSON(w, http.StatusOK, ord)
			}
		}
	}
}

func getOrder(server, uuid string) order {
	var ord_nil = order {}
	c := NewClient(server)
	ord, err := c.GetOrder(uuid)
	if err != nil {
		log.Fatal(err)
		return ord_nil
	} else {
		return ord
	}
}


// API Get Order Status - Concurrent - Get One
func gumballOrderStatusHandlerAny(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		
		params := mux.Vars(req)
		var uuid string = params["id"]
		fmt.Println( "Order Params ID: ", uuid )

		c1 := make(chan order)
    	c2 := make(chan order)
    	c3 := make(chan order)

		if uuid == ""  {
			formatter.JSON(w, http.StatusBadRequest, "Invalid Request. Order ID Missing.")
		} else {

			go getOrderServer1(uuid, c1) 
			go getOrderServer2(uuid, c2) 
			go getOrderServer3(uuid, c3) 

			var ord order
		  	select {
			    case ord = <-c1:
			        fmt.Println("Received Server1: ", ord)
			    case ord = <-c2:
			        fmt.Println("Received Server2: ", ord)
			    case ord = <-c3:
			        fmt.Println("Received Server3: ", ord)
		    }

			if ord == (order{}) {
				formatter.JSON(w, http.StatusBadRequest, "")
			} else {
				fmt.Println( "Order: ", ord )
				formatter.JSON(w, http.StatusOK, ord)
			}
		}
	}
}

func getOrderServer1(uuid string, chn chan<- order) {
	var ord_nil = order {}
	c := NewClient(server1)
	ord, err := c.GetOrder(uuid)
	if err != nil {
		chn <- ord_nil
	} else {
		fmt.Println( "Server1: ", ord)
		chn <- ord
	}
}

func getOrderServer2(uuid string, chn chan<- order) {
	var ord_nil = order {}
	c := NewClient(server2)
	ord, err := c.GetOrder(uuid)
	if err != nil {
		chn <- ord_nil
	} else {
		fmt.Println( "Server2: ", ord)
		chn <- ord
	}
}

func getOrderServer3(uuid string, chn chan<- order) {
	var ord_nil = order {}
	c := NewClient(server3)
	ord, err := c.GetOrder(uuid)
	if err != nil {
		chn <- ord_nil
	} else {
		fmt.Println( "Server3: ", ord)
		chn <- ord
	}
}


// API Process Order
func gumballProcessOrdersHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {

		params := mux.Vars(req)
		var uuid string = params["id"]
		fmt.Println( "Order Params ID: ", uuid )
		value := "Order Processed"

		if uuid == ""  {
			formatter.JSON(w, http.StatusBadRequest, "Invalid Request. Order ID Missing.")
		} else {
			c1 := NewClient(server1)
			ord, err := c1.UpdateOrder(uuid, value)
			if err != nil {
				log.Fatal(err)
				formatter.JSON(w, http.StatusBadRequest, err)
			} else {
				formatter.JSON(w, http.StatusOK, ord)
			}
		}
	}
}



/*

-- Setup Riak Bucket

riak ping
riak-admin test
riak-admin bucket-type create gumball '{"props":{"search_index":"orders"}}'
riak-admin bucket-type activate gumball

-- Gumball MongoDB Create Database

lsb_release -a 	// get version of ubuntu / 14.01:

sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 9DA31620334BD75D9DCB49F368818C72E52529D4
echo "deb [ arch=amd64 ] https://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/4.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.0.list
apt-get update
apt-get install mongodb-org-shell

mongo gumball_mongo_1:27017/cmpe281 

use cmpe281
show collections

Database Name: cmpe281
Collection Name: gumball

-- Gumball MongoDB Collection (Create Document) --

db.gumball.insert(
    { 
      Id: 1,
      CountGumballs: NumberInt(202),
      ModelNumber: 'M102988',
      SerialNumber: '1234998871109' 
    }
) ;

-- Gumball MongoDB Collection - Find Gumball Document --

db.gumball.find( { Id: 1 } ) ;

{
    "_id" : ObjectId("54741c01fa0bd1f1cdf71312"),
    "Id" : 1,
    "CountGumballs" : 202,
    "ModelNumber" : "M102988",
    "SerialNumber" : "1234998871109"
}

-- Gumball MongoDB Collection - Update Gumball Document --

db.gumball.update( 
    { Dd: 1 }, 
    { $set : { CountGumballs : NumberInt(10) } },
    { multi : false } 
)

-- Gumball Delete Documents

db.gumball.remove({})

*/

  


