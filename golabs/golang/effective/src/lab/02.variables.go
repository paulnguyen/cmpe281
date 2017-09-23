package main

/*

	https://golang.org/doc/effective_go.html

	Variables:

	Grouping can also indicate relationships between items, such as the fact
	that a set of variables is protected by a mutex.

*/

import (
	"fmt"
	"sync"
)

var singleVar string = "Hello World!"

var (
	countLock   sync.Mutex
	inputCount  uint32 = 1
	outputCount uint32 = 1
	errorCount  uint32 = 0
)

func main() {
	fmt.Println( "Go Variables" )
	fmt.Println( singleVar, inputCount, outputCount, errorCount, countLock )
}
