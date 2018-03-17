package main

import "fmt"

/*

	https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_035.html

	By default, the elements of a new array variable are initially set to the zero
	value for the element type, which is 0 for numbers.

*/

func main() {

	/*

		The literal syntax is similar for arrays, slices, maps, and structs. The
		specific form:
		
		q3 := [3]int{1, 2, 3}

		is a list of values in order, but it is also possible
		to specify a list of index and value pairs, like this:

	*/

	type Currency int
	const (
		USD Currency = iota  // see: https://github.com/golang/go/wiki/Iota
		EUR
		GBP
		RMB
	)

	symbol := [...]string{USD: "$", EUR: "€", GBP: "£", RMB: "¥"}
	fmt.Println(RMB, symbol[RMB]) // "3 ¥"

	// Also:
	list := [...]string{2: "A", 4: "B", 6: "C", 8: "D"}
	fmt.Println(list[0],"-",list[1],"-",list[2]) 
	fmt.Println(len(list))

}
