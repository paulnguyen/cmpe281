package main

import (
	"fmt"
	"sort"
)

func main() {

	ages4 := make(map[string]int)
	ages4["alice"] = 31
	ages4["charlie"] = 34
	fmt.Println(ages4)


	// Since we know the final size of names from the outset, it is more
	// efficient to allocate an array of the required size up front.
	// I.E.  Instead of:
	//
	// var names []string 
	//
	names := make([]string, 0, len(ages4)) // initially empty, but has capacity for ages4
	for name := range ages4 {
		names = append(names, name)
	}
	sort.Strings(names)
	for _, name := range names {
		fmt.Printf("%s\t%d\n", name, ages4[name])
	}

	/*

	 The zero value for a map type is nil, that is, a reference to no hash
	 table at all.

	 Most operations on maps, including lookup, delete, len, and range loops,
	 are safe to perform on a nil map reference, since it behaves like an
	 empty map. But storing to a nil map causes a panic:

	 ages["carol"] = 21 // panic: assignment to entry in nil map

	*/

	fmt.Println( "*** Handling NIL Maps ***")
	var ages map[string]int
	fmt.Println(ages == nil)    // "true"
	fmt.Println(len(ages) == 0) // "true"



}
