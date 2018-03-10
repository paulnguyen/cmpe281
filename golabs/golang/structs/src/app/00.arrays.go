package main

import "fmt"

/*

	https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_035.html

	By default, the elements of a new array variable are initially set to the zero
	value for the element type, which is 0 for numbers.

*/

func main() {

	fmt.Println( "*** Arrays Basics ***")
	var a [3] int                               // array of 3 integers
	fmt.Println("a[0] = ", a[0])               	// print the first element
	fmt.Println("a[len(a)-1] = ", a[len(a)-1]) 	// print the last element, a[2]

	// Print the indices and elements.
	fmt.Println( "*** Array Enumeration (Indices & Elements) ***")
	for i, v := range a {
		fmt.Printf("%d = %d\n", i, v)
	}

	// Print the elements only.
	fmt.Println( "*** Array Enumeration (Elements Only) ***")
	for _, v := range a {
		fmt.Printf("%d\n", v)
	}

	
}
