package app

/*

	https://golang.org/doc/effective_go.html

	Asanexample,there'snoneedtospendtimeliningupthecommentsonthefieldsofastructur
	e.Gofmtwilldothatforyou.Giventhedeclaration

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
	fmt.Println("Hello Effective Go")
}
