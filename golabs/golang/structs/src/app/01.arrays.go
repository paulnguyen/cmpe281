package main

import "fmt"

/*

	https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_035.html

	By default, the elements of a new array variable are initially set to the zero
	value for the element type, which is 0 for numbers.

*/

func main() {

	/*

		We can use an array literal to initialize an array with a list of values

	*/

	fmt.Println( "*** Array Literals ***")
	var q [3]int = [3]int{1, 2, 3}
	var r [3]int = [3]int{1, 2}

	fmt.Println("q = ", q)
	fmt.Println("r = ", r)
	fmt.Println("q[2] = ", q[2]) // "3"
	fmt.Println("r[2] = ", r[2]) // "0"

	
}
