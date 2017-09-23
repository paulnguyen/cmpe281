package main

/*

	https://golang.org/doc/effective_go.html

	Channels

	Like maps, channels are allocated with make, and the resulting value acts
	as a reference to an underlying data structure. If an optional integer
	parameter is provided, it sets the buffer size for the channel. The
	default is zero, for an unbuffered or synchronous channel.

	ci := make(chan int)            // unbuffered channel of integers
	cj := make(chan int, 0)         // unbuffered channel of integers
	cs := make(chan *os.File, 100)  // buffered channel of pointers to Files

	Unbuffered channels combine communication—the exchange of a value—with
	synchronization—guaranteeing that two calculations (goroutines) are in a
	known state.

	There are lots of nice idioms using channels. Here's one to get us
	started. In the previous section we launched a sort in the background. A
	channel can allow the launching goroutine to wait for the sort to
	complete.

*/

import ( 
	"fmt"
	"time"
)

func main() {

	fmt.Println("Go Concurrency")

	c := make(chan int)  // Allocate a channel.

	// Start the sort in a goroutine; when it completes, signal on the channel.
	go func() {
		fmt.Println( "Starting Sort...")
	    time.Sleep( 10000000000 * time.Nanosecond ) // i.e. list.Sort()
	    fmt.Println( "Sort Done. Signaling Channel!")
	    c <- 1  // Send a signal; value does not matter.
	}()

	doSomethingForAWhile()
	fmt.Println( "Waiting for Sort to Finish...")
	<-c   // Wait for sort to finish; discard sent value.

}

// Receivers always block until there is data to receive. If the channel is
// unbuffered, the sender blocks until the receiver has received the value. If
// the channel has a buffer, the sender blocks only until the value has been
// copied to the buffer; if the buffer is full, this means waiting until some
// receiver has retrieved a value.

func doSomethingForAWhile() {
	fmt.Println( "Starting doSomethingForAWhile...")
	time.Sleep(  5000000000 * time.Nanosecond )
	fmt.Println( "doSomethingForAWhile Done!")
}





