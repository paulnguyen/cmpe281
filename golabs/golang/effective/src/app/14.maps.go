package main

import (
	"fmt"
	"sort"
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

	fmt.Println("*** Map Basics ***")
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

	fmt.Println("*** Map Access ***")
	ages4["alice"] = 32
	fmt.Println(ages4["alice"]) // "32"

	/* Remove elements works with the built-in function delete: */

	fmt.Println("*** Map Delete ***")
	delete(ages4, "alice") // remove element ages["alice"]
	fmt.Println(ages4)

	/*

	 All of these operations are safe even if the element isn’t in the map; a
	 map lookup using a key that isn’t present returns the zero value for its
	 type, so, for instance, the following works even when "bob" is not yet a
	 key in the map because the value of ages["bob"] will be 0.

	*/

	fmt.Println("*** Adding Bob into the Map ***")
	ages4["bob"] = ages4["bob"] + 1 // happy birthday!
	fmt.Println(ages4)

	/*

		The shorthand assignment forms x += y and x++ also work for map
		elements, so we can rewrite the statement above as

	*/

	ages4["bob"] += 1
	fmt.Println(ages4)

	/* or even more concisely as... */

	ages4["bob"]++
	fmt.Println(ages4)

	/*
		 But a map element is not a variable, and we cannot take its address:

			_ = &ages4["bob"] // compile error: cannot take address of map element

		One reason that we can’t take the address of a map element is that growing
		a map might cause rehashing of existing elements into new storage
		locations, thus potentially invalidating the address.
	*/

	/*

		To enumerate all the key/value pairs in the map, we use a range-based
		for loop similar to those we saw for slices. Successive iterations of
		the loop cause the name and age variables to be set to the next
		key/value pair:

		Note: The order of map iteration is unspecified, and different
		implementations might use a different hash function, leading to a
		different ordering. In practice, the order is random,

	*/

	fmt.Println("*** Enumeration ***")
	for name, age := range ages4 {
		fmt.Printf("%s\t%d\n", name, age)
	}

	/*

		To enumerate the key/value pairs in order, we must sort the keys
		explicitly, for instance, using the Strings function from the sort
		package if the keys are strings. This is a common pattern:

	*/

	fmt.Println("*** Sorted ***")
	ages4["tom"] = ages4["tom"] + 1
	ages4["cindy"] = ages4["cindy"] + 1
	ages4["mary"] = ages4["mary"] + 1

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

	fmt.Println("*** Handling NIL Maps ***")
	var ages map[string]int
	fmt.Println(ages == nil)    // "true"
	fmt.Println(len(ages) == 0) // "true"

}
