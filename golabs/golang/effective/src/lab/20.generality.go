package main

/*

	https://golang.org/doc/effective_go.html

	Generality

	If a type exists only to implement an interface and will never have
	exported methods beyond that interface, there is no need to export the
	type itself. Exporting just the interface makes it clear the value has no
	interesting behavior beyond what is described in the interface. It also
	avoids the need to repeat the documentation on every instance of a common
	method.

	In such cases, the constructor should return an interface value rather than the implementing type.

	As an example, in the hash libraries both crc32.NewIEEE and adler32.New
	return the interface type hash.Hash32. Substituting the CRC-32 algorithm
	for Adler-32 in a Go program requires only changing the constructor call;
	the rest of the code is unaffected by the change of algorithm.

*/

import (
	"fmt"
	"net/http"
)

func main() {
	fmt.Println("Generality in Go")
}

// Interfaces and methods - Since almost anything can have methods attached,
// almost anything can satisfy an interface. One illustrative example is in
// the http package, which defines the Handler interface. Any object that
// implements Handler can serve HTTP requests.

type Handler interface {
    ServeHTTP(http.ResponseWriter, *http.Request)
}

