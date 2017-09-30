package main

/*

	https://golang.org/doc/effective_go.html

	The built-in function make(T, args) serves a purpose different from new(T). It
	creates slices, maps, and channels only, and it returns an initialized (not
	zeroed) value of type T (not *T). The reason for the distinction is that these
	three types represent, under the covers, references to data structures that
	must be initialized before use. A slice, for example, is a three-item
	descriptor containing a pointer to the data (inside an array), the length, and
	the capacity, and until those items are initialized, the slice is nil. For
	slices, maps, and channels, make initializes the internal data structure and
	prepares the value for use.

*/

import (
	"fmt"
)

func main() {

	fmt.Println("===== slices =====")

	// Allocate an array of 100 ints and then creates a slice structure with
	// length 10 and a capacity of 100 pointing at the first 10 elements of
	// the array.

	slice := make([]int, 10, 100)
	fmt.Println("slice = ", slice)

	// A pointer to a newly allocated, zeroed slice
	// structure, that is, a pointer to a nil slice value.

	nil_slice := new([]int)
	fmt.Println("nil_slice = ", nil_slice)

	// make applies only to maps, slices and channels and does not return a
	// pointer. To obtain an explicit pointer allocate with new or take the
	// address of a variable explicitly.

	var p *[]int = new([]int)      // allocates slice structure; *p == nil; rarely useful
	var v []int = make([]int, 100) // the slice v now refers to a new array of 100 ints

	// Unnecessarily complex:
	var p2 *[]int = new([]int)
	*p2 = make([]int, 100, 100)

	// Idiomatic:
	v3 := make([]int, 100)

	// To avoid Go compiler errors
	fmt.Println( p, v, v3 )
}
