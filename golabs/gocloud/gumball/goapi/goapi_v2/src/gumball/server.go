/*
	Gumball API in Go (Version 2)
	Process Order with Go Channels and Mutex
*/

package main

import (
	"encoding/json"
	"fmt"
	"time"

	"github.com/codegangsta/negroni"
	"github.com/gorilla/mux"
	"github.com/satori/go.uuid"
	"github.com/unrolled/render"
	"net/http"
)

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

// Init Background Processes
func init() {
	for i := 1; i < 2; i++ {
		go gumballOrdersWorker()
	}
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

// API Ping Handler
func pingHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		formatter.JSON(w, http.StatusOK, struct{ Test string }{"API version 2.0 alive!"})
	}
}

// API Gumball Machine Handler
func gumballHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		formatter.JSON(w, http.StatusOK, machine)
	}
}

// API Update Gumball Inventory
func gumballUpdateHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		var m gumballMachine
		_ = json.NewDecoder(req.Body).Decode(&m)
		fmt.Println("Update Gumball Inventory To: ", m.CountGumballs)
		mutex.Lock()
		machine.CountGumballs = m.CountGumballs
		mutex.Unlock()
		formatter.JSON(w, http.StatusOK, machine)
	}
}

// API Create New Gumball Order
func gumballNewOrderHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		uuid, _ := uuid.NewV4()
		var ord = order{
			Id:          uuid.String(),
			OrderStatus: "Order Placed",
		}
		if orders == nil {
			orders = make(map[string]order)
		}
		orders[uuid.String()] = ord
		fmt.Println("Orders: ", orders)
		formatter.JSON(w, http.StatusOK, ord)
	}
}

// API Get Order Status
func gumballOrderStatusHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		params := mux.Vars(req)
		var uuid string = params["id"]
		fmt.Println("Order ID: ", uuid)
		if uuid == "" {
			fmt.Println("Orders:", orders)
			var orders_array []order
			for key, value := range orders {
				fmt.Println("Key:", key, "Value:", value)
				orders_array = append(orders_array, value)
			}
			formatter.JSON(w, http.StatusOK, orders_array)
		} else {
			var ord = orders[uuid]
			fmt.Println("Order: ", ord)
			formatter.JSON(w, http.StatusOK, ord)
		}
	}
}

// API Process Orders
func gumballProcessOrdersHandler(formatter *render.Render) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		for key, _ := range orders {
			var order = orders[key]
			if order.OrderStatus == "Order Placed" {
				order_queue <- key	
			}
		}
		formatter.JSON(w, http.StatusOK, "Processing Orders...")
	}
}


// Server Orders from Order Queue
func gumballOrdersWorker() {
	for {
		order_key := <- order_queue
		mutex.Lock()
		var order = orders[order_key]
		time.Sleep(5000 * time.Millisecond)
		order.OrderStatus = "Order Processed"
		orders[order_key] = order
		cnt := machine.CountGumballs
		cnt = cnt - 1		
		machine.CountGumballs = cnt
		mutex.Unlock()
		fmt.Println("Processed Order: ", order)
	}
}

