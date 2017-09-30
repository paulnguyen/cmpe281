package main

/*

	https://golang.org/doc/effective_go.html

	Concurrency
	Share by communicating

	Concurrent programming is a large topic and there is space only for some
	Go-specific highlights here.

	Concurrent programming in many environments is made difficult by the
	subtleties required to implement correct access to shared variables. Go
	encourages a different approach in which shared values are passed around
	on channels and, in fact, never actively shared by separate threads of
	execution. Only one goroutine has access to the value at any given time.
	Data races cannot occur, by design. To encourage this way of thinking we
	have reduced it to a slogan:

    Do not communicate by sharing memory; instead, share memory by communicating. 

	This approach can be taken too far. Reference counts may be best done by
	putting a mutex around an integer variable, for instance. But as a high-
	level approach, using channels to control access makes it easier to write
	clear, correct programs.

	One way to think about this model is to consider a typical single-threaded
	program running on one CPU. It has no need for synchronization primitives.
	Now run another such instance; it too needs no synchronization. Now let
	those two communicate; if the communication is the synchronizer, there's
	still no need for other synchronization. Unix pipelines, for example, fit
	this model perfectly. Although Go's approach to concurrency originates in
	Hoare's Communicating Sequential Processes (CSP), it can also be seen as a
	type-safe generalization of Unix pipes. 

	Goroutines

	They're called goroutines because the existing terms—threads, coroutines,
	processes, and so on—convey inaccurate connotations. A goroutine has a
	simple model: it is a function executing concurrently with other
	goroutines in the same address space. It is lightweight, costing little
	more than the allocation of stack space. And the stacks start small, so
	they are cheap, and grow by allocating (and freeing) heap storage as
	required.

	Goroutines are multiplexed onto multiple OS threads so if one should
	block, such as while waiting for I/O, others continue to run. Their design
	hides many of the complexities of thread creation and management.

	Prefix a function or method call with the go keyword to run the call in a
	new goroutine. When the call completes, the goroutine exits, silently.
	(The effect is similar to the Unix shell's & notation for running a
	command in the background.)

	go list.Sort()  // run list.Sort concurrently; don't wait for it.

*/

import ( 
	"fmt"
	"time"
)

func main() {
	fmt.Println("Go Concurrency")
	Announce( "Hello Go!", 1 * time.Minute)
	time.Sleep( 2 * time.Minute )
}


// A function literal can be handy in a goroutine invocation.

func Announce(message string, delay time.Duration) {
    go func() {
        time.Sleep(delay)
        fmt.Println(message)
    }()  // Note the parentheses - must call the function.
}

// In Go, function literals are closures: the implementation makes sure the
// variables referred to by the function survive as long as they are active.





