package main

/*

	https://golang.org/doc/effective_go.html

	Format:

	As an example, there's no need to spend time lining up the comments on the
	fields of a structure. Gofmt will do that for you. Given the declaration:

	type T struct {
	    name string // name of the object
	    value int // its value
	}

	gofmt will line up the columns:

	type T struct {
	    name    string // name of the object
	    value   int    // its value
	}

*/

import "fmt"

type T struct {
	name  string // name of the object
	value int    // its value
}

func main() {
	fmt.Println("Go Formatting")
}
