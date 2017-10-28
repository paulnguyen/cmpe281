package main

type gumballMachine struct {
	Id            int
	CountGumballs int
	ModelNumber   string
	SerialNumber  string
}

var machine gumballMachine = gumballMachine{
	Id:            1,
	CountGumballs: 900,
	ModelNumber:   "M102988",
	SerialNumber:  "1234998871109",
}

type order struct {
	Id          string
	OrderStatus string
}

var orders map[string]order
var order_queue = make(chan string, 10)
var order_update = make(chan int, 1000)
