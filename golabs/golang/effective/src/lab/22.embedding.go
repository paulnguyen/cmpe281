package main

/*

	https://golang.org/doc/effective_go.html

	Embedding

	Go does not provide the typical, type-driven notion of subclassing, but it
	does have the ability to “borrow” pieces of an implementation by embedding
	types within a struct or interface.

*/

import "fmt"

func main() {
	fmt.Println("Go Reuse - Embedding")
	StructEmbeddingTest()
}

// Interface embedding is very simple. We've mentioned the io.Reader and
// io.Writer interfaces before; here are their definitions.

type Reader interface {
    Read(p []byte) (n int, err error)
}

type Writer interface {
    Write(p []byte) (n int, err error)
}

// The io package also exports several other interfaces that specify objects
// that can implement several such methods. For instance, there is
// io.ReadWriter, an interface containing both Read and Write.

// ReadWriter is the interface that combines the Reader and Writer interfaces.

type ReadWriter interface {
    Reader
    Writer
}

// This says just what it looks like: A ReadWriter can do what a Reader does
// and what a Writer does; it is a union of the embedded interfaces (which
// must be disjoint sets of methods). Only interfaces can be embedded within
// interfaces.


// The same basic idea applies to structs, but with more far-reaching
// implications. The bufio package has two struct types, bufio.Reader and
// bufio.Writer, each of which of course implements the analogous interfaces
// from package io. And bufio also implements a buffered reader/writer, which
// it does by combining a reader and a writer into one struct using embedding:
// it lists the types within the struct but does not give them field names.

func StructEmbeddingTest() {

	fmt.Println("*** Struct Embedding and Anonymous Fields (Struct Literals) ***")

	type Point struct {
		X, Y int
	}

	type Circle struct {
		Point
		Radius int
	}

	type Wheel struct {
		Circle
		Spokes int
	}

	var w Wheel
	w = Wheel{Circle{Point{8, 8}, 5}, 20}

	fmt.Printf("%#v\n", w)

	// Output:
	// Wheel{Circle:Circle{Point:Point{X:8, Y:8}, Radius:5}, Spokes:20}

	w = Wheel{
		Circle: Circle{
			Point:  Point{X: 8, Y: 8},
			Radius: 5,
		},
		Spokes: 20, // NOTE: trailing comma necessary here (and at Radius)
	}

	w.X = 42

	fmt.Printf("%#v\n", w)
	
	// Output:
	// Wheel{Circle:Circle{Point:Point{X:42, Y:8}, Radius:5}, Spokes:20}

}

