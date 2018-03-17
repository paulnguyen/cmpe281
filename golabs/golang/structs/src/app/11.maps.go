package main

import (
	"fmt"
)

func main() {

	ages4 := make(map[string]int)
	ages4["alice"] = 31
	ages4["charlie"] = 34
	fmt.Println(ages4)

	/*

		To enumerate all the key/value pairs in the map, we use a range-based
		for loop similar to those we saw for slices. Successive iterations of
		the loop cause the name and age variables to be set to the next
		key/value pair:

		Note: The order of map iteration is unspecified, and different
		implementations might use a different hash function, leading to a
		different ordering. In practice, the order is random,

	*/

	fmt.Println( "*** Enumeration ***")
	for name, age := range ages4 {
		fmt.Printf("%s\t%d\n", name, age)
	}

	/*

		To enumerate the key/value pairs in order, we must sort the keys
		explicitly, for instance, using the Strings function from the sort
		package if the keys are strings. This is a common pattern:

	*/

	fmt.Println( "*** Sorted ***")
	ages4["tom"] = ages4["tom"] + 1 
	ages4["cindy"] = ages4["cindy"] + 1 
	ages4["mary"] = ages4["mary"] + 1 

	for name, age := range ages4 {
		fmt.Printf("%s\t%d\n", name, age)
	}


}
