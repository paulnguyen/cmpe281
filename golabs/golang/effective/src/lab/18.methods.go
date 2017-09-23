package main

/*

	https://golang.org/doc/effective_go.html

	Go Methods

	Methods can be defined for any named type (except a pointer or an
	interface); the receiver does not have to be a struct.

*/

import "fmt"

func main() {

	fmt.Println("Go Methods")

	var a ByteSlice 
	a.Append1( []byte{1,2,3} )
	a.Append2( []byte{4,5,6} )
	fmt.Println( a )
}

// In the discussion of slices above, we wrote an Append function. We can
// define it as a method on slices instead. To do this, we first declare a
// named type to which we can bind the method, and then make the receiver
// for the method a value of that type.

type ByteSlice []byte

func (slice ByteSlice) Append1(data []byte) []byte {
    // Body exactly the same as the Append function defined above.
    return []byte{}
}

// This still requires the method to return the updated slice. We can
// eliminate that clumsiness by redefining the method to take a pointer to a
// ByteSlice as its receiver, so the method can overwrite the caller's slice.

func (p *ByteSlice) Append2(data []byte) {
    slice := *p
    // Body as above, without the return.
    *p = slice
}


/*

func Append(slice, data []byte) []byte {
    l := len(slice)
    if l + len(data) > cap(slice) {  // reallocate
        // Allocate double what's needed, for future growth.
        newSlice := make([]byte, (l+len(data))*2)
        // The copy function is predeclared and works for any slice type.
        copy(newSlice, slice)
        slice = newSlice
    }
    slice = slice[0:l+len(data)]
    for i, c := range data {
        slice[l+i] = c
    }
    return slice
}

*/
