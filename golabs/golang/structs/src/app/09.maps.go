package main

import (
	"fmt"
)

func main() {

	/*

		https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_037.html
	
		In Go, a map is a reference to a hash table, and a map type is written
		map[K]V, where K and V are the types of its keys and values. All of the keys
		in a given map are of the same type, and all of the values are of the same
		type, but the keys need not be of the same type as the values. The key type K
		must be comparable using ==, so that the map can test whether a given key is
		equal to one already within it.

	*/

	fmt.Println( "*** Map Basics ***")
	ages1 := make(map[string]int) // mapping from strings to ints
	fmt.Println(ages1)

	/* An alternative expression for a new empty map is: */

	ages2 := map[string]int{}
	fmt.Println(ages2)

	/*

	 We can also use a map literal to create a new map populated with some
	 initial key/value pairs:

	*/

	ages3 := map[string]int{
		"alice":   31,
		"charlie": 34,
	}
	fmt.Println(ages3)

	/* This is equivalent to */

	ages4 := make(map[string]int)
	ages4["alice"] = 31
	ages4["charlie"] = 34
	fmt.Println(ages4)

	/* Map elements are accessed through the usual subscript notation: */

	fmt.Println( "*** Map Access ***")
	ages4["alice"] = 32
	fmt.Println(ages4["alice"]) // "32"

	/* Remove elements works with the built-in function delete: */

	fmt.Println( "*** Map Delete ***")
	delete(ages4, "alice") // remove element ages["alice"]
	fmt.Println(ages4)


}
