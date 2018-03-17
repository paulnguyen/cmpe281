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

	 All of these operations are safe even if the element isn’t in the map; a
	 map lookup using a key that isn’t present returns the zero value for its
	 type, so, for instance, the following works even when "bob" is not yet a
	 key in the map because the value of ages["bob"] will be 0.

	*/

	fmt.Println( "*** Adding Bob into the Map ***")
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


}
