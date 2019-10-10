/*
	Gumball API in Go 
	Uses MySQL & Redis
*/

package main

import (
	"fmt"
	"log"
	"net/http"
	"encoding/json"
	"github.com/codegangsta/negroni"
	"github.com/gorilla/mux"
	"github.com/unrolled/render"
	"github.com/satori/go.uuid"
    "database/sql"
	_ "github.com/go-sql-driver/mysql"
	"github.com/go-redis/redis"
)

/*
	Go's SQL Package:  
		Tutorial: http://go-database-sql.org/index.html
		Reference: https://golang.org/pkg/database/sql/

	Go's Redis Package:
		Github: https://github.com/go-redis/redis
		Example: https://github.com/go-redis/redis/blob/master/example_test.go
*/

//var mysql_connect = "root:cmpe281@tcp(127.0.0.1:3306)/cmpe281"
var mysql_connect = "root:cmpe281@tcp(mysql:3306)/cmpe281"

//var redis_connect = "localhost:6379"
var redis_connect = "redis:6379"


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

// Init MySQL & Redis DB Connections

var redis_client *redis.Client

func init() {

	// Test Redis Connection
	redis_client := redis.NewClient(&redis.Options{
		Addr:     redis_connect,
		Password: "", // no password set
		DB:       0,  // use default DB
	})

	pong, err := redis_client.Ping().Result()
	fmt.Println(pong, err)

	// Test MySQL Connection
	db, err := sql.Open("mysql", mysql_connect)
	if err != nil {
		log.Fatal(err)
	} else {
		var (
			id int
			count int
			model string
			serial string
		)
		rows, err := db.Query("select id, count_gumballs, model_number, serial_number from gumball where id = ?", 1)
		if err != nil {
			log.Fatal(err)
		}
		defer rows.Close()
		for rows.Next() {
			err := rows.Scan(&id, &count, &model, &serial)
			if err != nil {
				log.Fatal(err)
			}
			log.Println(id, count, model, serial)
		}
		err = rows.Err()
		if err != nil {
			log.Fatal(err)
		}
	}
	defer db.Close()

}


// API Routes
func initRoutes(mx *mux.Router, formatter *render.Render) {
	mx.HandleFunc("/ping", pingHandler(formatter)).Methods("GET")
	mx.HandleFunc("/gumball", gumballHandler(formatter)).Methods("GET")
	mx.HandleFunc("/gumball", gumballUpdateHandler(formatter)).Methods("PUT")
	mx.HandleFunc("/order", gumballNewOrderHandler(formatter)).Methods("POST")
	mx.HandleFunc("/order", gumballOrderStatusHandler(formatter)).Methods("GET")
	mx.HandleFunc("/order/{id}", gumballOrderStatusHandler(formatter)).Methods("GET")
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

		var (
			id int
			count int
			model string
			serial string
		)
		db, err := sql.Open("mysql", mysql_connect)
		defer db.Close()
		if err != nil {
			log.Fatal(err)
		} else {
			rows, _ := db.Query("select id, count_gumballs, model_number, serial_number from gumball where id = ?", 1)
			defer rows.Close()
			for rows.Next() {
				rows.Scan(&id, &count, &model, &serial)
				log.Println(id, count, model, serial)
			}
		}
		result := gumballMachine {
			Id : id,
			CountGumballs : count,
			ModelNumber : model,
			SerialNumber : serial,
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
	
		var (
			id int
			count int
			model string
			serial string
		)
		db, err := sql.Open("mysql", mysql_connect)
		defer db.Close()
		stmt, err := db.Prepare("update gumball set count_gumballs = ? where id = ?")
		if err != nil {
			log.Fatal(err)
		}
		_, err = stmt.Exec( m.CountGumballs, 1)
		if err != nil {
			log.Fatal(err)
		}

		if err != nil {
			log.Fatal(err)
		} else {
			rows, _ := db.Query("select id, count_gumballs, model_number, serial_number from gumball where id = ?", 1)
			defer rows.Close()
			for rows.Next() {
				rows.Scan(&id, &count, &model, &serial)
				log.Println(id, count, model, serial)
			}
		}
		result := gumballMachine {
			Id : id,
			CountGumballs : count,
			ModelNumber : model,
			SerialNumber : serial,
		}

        fmt.Println("Gumball Machine:", result )
		formatter.JSON(w, http.StatusOK, result)
	}
}

// API Create New Gumball Order
func gumballNewOrderHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {

		redis_client := redis.NewClient(&redis.Options{
			Addr:     redis_connect,
			Password: "", // no password set
			DB:       0,  // use default DB
		})

		uuid := uuid.NewV4()
    	var ord = order {
					Id: uuid.String(),            		
					OrderStatus: "Order Placed",
		}
		err := redis_client.Set(uuid.String(), "Order Placed", 0).Err()
		if err != nil {
			panic(err)
		}
		fmt.Println( "Order: ", ord )
		formatter.JSON(w, http.StatusOK, ord)
	}
}

// API Get Order Status
func gumballOrderStatusHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {

		redis_client := redis.NewClient(&redis.Options{
			Addr:     redis_connect,
			Password: "", // no password set
			DB:       0,  // use default DB
		})

		params := mux.Vars(req)
		var uuid string = params["id"]
		fmt.Println( "Order ID: ", uuid )

		if uuid == ""  {
			var keys = redis_client.Keys("*").Val()
			fmt.Println( "Orders:", keys )
			var orders_array [] order
			for _, key := range keys {
				val, _ := redis_client.Get(key).Result()
    			fmt.Println("Key:", key, "Value:", val)
		   		var ord = order {
					Id: key,            		
					OrderStatus: val,
				}
    			orders_array = append(orders_array, ord)
			}
			formatter.JSON(w, http.StatusOK, orders_array)
		} else {
			val, _ := redis_client.Get(uuid).Result()
		   	var ord = order {
							Id: uuid,            		
							OrderStatus: val,
				}
			fmt.Println( "Order: ", ord )
			formatter.JSON(w, http.StatusOK, ord)
		}
	}
}

// API Process Orders 
func gumballProcessOrdersHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {

		redis_client := redis.NewClient(&redis.Options{
			Addr:     redis_connect,
			Password: "", // no password set
			DB:       0,  // use default DB
		})

		params := mux.Vars(req)
		var uuid string = params["id"]
		fmt.Println( "Order ID: ", uuid )

		if uuid == ""  {
			formatter.JSON(w, http.StatusOK, "Order ID Missing!")
		} else {
			err := redis_client.Set(uuid, "Order Processed", 0).Err()
			if err != nil {
				formatter.JSON(w, http.StatusOK, "Order ID Error!")
			} else {
		   		var ord = order {
					Id: uuid,            		
					OrderStatus: "Order Processed",
				}
				formatter.JSON(w, http.StatusOK, ord)
			}
		}
	}
}


/*

	-- Create Database Schema (DB User: root, DB Pass: cmpe281)

		Database Schema: cmpe281

		create database cmpe281 ;
		use cmpe281 ;

	-- Create Database Table 

		CREATE TABLE gumball (
		id bigint(20) NOT NULL AUTO_INCREMENT,
		version bigint(20) NOT NULL,
		count_gumballs int(11) NOT NULL,
		model_number varchar(255) NOT NULL,
		serial_number varchar(255) NOT NULL,
		PRIMARY KEY (id),
		UNIQUE KEY serial_number (serial_number)
		) ;

	-- Load Data

		insert into gumball ( id, version, count_gumballs, model_number, serial_number ) 
		values ( 1, 0, 1000, 'M102988', '1234998871109' ) ;

	-- Verify Data 

		select * from gumball ;

	-- Check Redis Cache

		redis-cli			// start redis shell

		keys *				// find all keys
		get <key>			// get value of a key
		set <key> <value>	// create/update a record
		quit				// exit redis shell



*/

  


