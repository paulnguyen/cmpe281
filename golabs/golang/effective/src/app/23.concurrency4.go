package main

/*

	https://golang.org/doc/effective_go.html

	Going back to the general problem of writing the server, another approach
	that manages resources well is to start a fixed number of handle
	goroutines all reading from the request channel. The number of goroutines
	limits the number of simultaneous calls to process. This Serve function
	also accepts a channel on which it will be told to exit; after launching
	the goroutines it blocks receiving from that channel.

*/

import ( 
	"fmt"
	"time"
	"net/http"
)

const MaxOutstanding = 2

func main() {
	fmt.Println("Go Concurrency")
}

func handle(queue chan *http.Request) {
    for r := range queue {
        process(r)
    }
}

func Serve(clientRequests chan *http.Request, quit chan bool) {
    // Start handlers
    for i := 0; i < MaxOutstanding; i++ {
        go handle(clientRequests)
    }
    <-quit  // Wait to be told to exit.
}

func process( *http.Request ) {
	 time.Sleep( 10000000000 * time.Nanosecond ) 
}




