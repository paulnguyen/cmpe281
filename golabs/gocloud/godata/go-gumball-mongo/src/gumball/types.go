/*
	Gumball API in Go (Version 3)
	Uses MongoDB and RabbitMQ 
	(For use with Kong API Key)
*/
	
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
