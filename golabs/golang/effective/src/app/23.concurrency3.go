package main

/*

	https://golang.org/doc/effective_go.html

	A buffered channel can be used like a semaphore, for instance to limit
	throughput. In this example, incoming requests are passed to handle, which
	sends a value into the channel, processes the request, and then receives a
	value from the channel to ready the “semaphore” for the next consumer. The
	capacity of the channel buffer limits the number of simultaneous calls to
	process.

*/

import ( 
	"fmt"
	"time"
	"net/http"
)

// A buffered channel can be used like a semaphore, for instance to limit
// throughput. In this example, incoming requests are passed to handle, which
// sends a value into the channel, processes the request, and then receives a
// value from the channel to ready the “semaphore” for the next consumer. The
// capacity of the channel buffer limits the number of simultaneous calls to
// process.

func main() {
	fmt.Println("Go Concurrency")

}

const MaxOutstanding = 1

var sem = make(chan int, MaxOutstanding)

func Serve(queue chan *http.Request) {
    for {
        req := <-queue
        go handle(req)  // Don't wait for handle to finish.
    }
}

func handle(r *http.Request) {
    sem <- 1    // Wait for active queue to drain.
    process(r)  // May take a long time.
    <-sem       // Done; enable next request to run.
}

func process(r *http.Request ) {
	 time.Sleep( 10000000000 * time.Nanosecond ) 
}

// Once MaxOutstanding handlers are executing process, any more will block
// trying to send into the filled channel buffer, until one of the existing
// handlers finishes and receives from the buffer.

// This design has a problem, though: Serve creates a new goroutine for every
// incoming request, even though only MaxOutstanding of them can run at any
// moment. As a result, the program can consume unlimited resources if the
// requests come in too fast. We can address that deficiency by changing Serve
// to gate the creation of the goroutines. Here's an obvious solution, but
// beware it has a bug we'll fix subsequently:

func Serve2(queue chan *http.Request) {
    for req := range queue {
        sem <- 1
        go func() {
            process(req) // Buggy; see explanation below.
            <-sem
        }()
    }
}

// The bug is that in a Go for loop, the loop variable is reused for each
// iteration, so the req variable is shared across all goroutines. That's not
// what we want. We need to make sure that req is unique for each goroutine.
// Here's one way to do that, passing the value of req as an argument to the
// closure in the goroutine:

func Serve3(queue chan *http.Request) {
    for req := range queue {
        sem <- 1
        go func(req *http.Request) {
            process(req)
            <-sem
        }(req)
    }
}

// Compare this version with the previous to see the difference in how the
// closure is declared and run. Another solution is just to create a new
// variable with the same name, as in this example:

func Serve4(queue chan *http.Request) {
    for req := range queue {
        req := req // Create new instance of req for the goroutine.
        sem <- 1
        go func() {
            process(req)
            <-sem
        }()
    }
}

// It may seem odd to write
//
// 	req := req
//
// but it's legal and idiomatic in Go to do this. You get a fresh version of
// the variable with the same name, deliberately shadowing the loop variable
// locally but unique to each goroutine.





