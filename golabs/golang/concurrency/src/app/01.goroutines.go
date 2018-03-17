/* 
	https://gobyexample.com/goroutines
	A _goroutine_ is a lightweight thread of execution.	
*/

package main

import (
    "fmt"
    "time"
)

func f(from string) {
    for i := 0; i < 3; i++ {
        fmt.Println(from, ":", i)
        time.Sleep(5000 * time.Millisecond)
    }
    fmt.Println("func f() done!")
}

func main() {

    // Suppose we have a function call `f(s)`. Here's how
    // we'd call that in the usual way, running it
    // synchronously.
    f("direct")

    // To invoke this function in a goroutine, use
    // `go f(s)`. This new goroutine will execute
    // concurrently with the calling one.
    go f("goroutine")

    // You can also start a goroutine for an anonymous
    // function call.
    go func(msg string) {
        fmt.Println("Anonymous: " + msg)
        time.Sleep(5000 * time.Millisecond)
        fmt.Println("Anonymous: done")
    }("going")

    // Our two function calls are running asynchronously in
    // separate goroutines now, so execution falls through
    // to here. This `Scanln` code requires we press a key
    // before the program exits.
    var input string
    fmt.Println("Waiting for key...")
    fmt.Scanln(&input)
    fmt.Println("Got Key!")
}


