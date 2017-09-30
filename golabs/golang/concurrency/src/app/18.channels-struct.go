/*

	https://gobyexample.com/channels

	Channels are the pipes that connect concurrent
	goroutines. You can send values into channels from one
	goroutine and receive those values into another
	goroutine.

*/

package main

import "fmt"

var channel = make(chan Data, 2)
type Data struct {
    Key      string
    Value    string
}

func main() {

    go func() { channel <- Data{ "mykey", "mval" } }()
    d := <-channel
    fmt.Println(d)
}
