package main

import "fmt"

/*

	https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_035.html

	By default, the elements of a new array variable are initially set to the zero
	value for the element type, which is 0 for numbers.

*/

func main() {

	/*

		Indices can appear in any order and some may be omitted;as before,
		unspecified values take on the zero value for the element type.

		The following defines an array r with 100 elements, all zero except for
		the last, which has value −1.

	*/

	fmt.Println( "*** Sparse Arrays ***")
	array1 := [...]int{99: -1}
	fmt.Println("array1 = ", array1)

}
