package main

import "fmt"

/*

	https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_035.html

	By default, the elements of a new array variable are initially set to the zero
	value for the element type, which is 0 for numbers.

*/

func main() {

	/*

	 If an array’s element type is comparable then the array type is
	 comparable too, so we may directly compare two arrays of that type using
	 the == operator, which reports whether all corresponding elements are
	 equal. The != operator is its negation.

	*/

	fmt.Println( "*** Array Comparisons ***")
	
	a1 := [2]int{1, 2}
	b1 := [...]int{1, 2}
	c1 := [2]int{1, 3}

	fmt.Println(a1 == b1, a1 == c1, b1 == c1) // "true false false"
	
	//d := [3]int{1, 2}
	//fmt.Println(a == d) // compile error: cannot compare [2]int == [3]int
}
