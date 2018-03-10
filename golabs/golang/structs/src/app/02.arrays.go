package main

import "fmt"

/*

	https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_035.html

	By default, the elements of a new array variable are initially set to the zero
	value for the element type, which is 0 for numbers.

*/

func main() {


	/*

		In an array literal, if an ellipsis “...” appears in place of the length,
		the array length is determined by the number of initializers.

	*/

	q2 := [...]int{1, 2, 3}
	fmt.Printf("%T\n", q2) // "[3]int"

	/*

		The size of an array is part of its type, so [3]int and [4]int are
		different types

	*/

	q3 := [3]int{1, 2, 3}
	q3 = [4]int{1, 2, 3, 4}   // compile error: cannot assign [4]int to [3]int

	fmt.Printf("%T\n", q3) // "[3]int"

}
