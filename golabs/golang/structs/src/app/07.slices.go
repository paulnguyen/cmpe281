package main

import "fmt"

func main() {

	/*

	   https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_036.html

	   Arrays and slices are intimately connected. A slice is a lightweight data
	   structure that gives access to a subsequence (or perhaps all) of the elements
	   of an array, which is known as the slice’s underlying array. A slice has three
	   components: a pointer, a length, and a capacity. The pointer points to the
	   first element of the array that is reachable through the slice, which is not
	   necessarily the array’s first element. The length is the number of slice
	   elements; it can’t exceed the capacity, which is usually the number of
	   elements between the start of the slice and the end of the underlying array.
	   The built-in functions len and cap return those values.

	*/

	fmt.Println( "*** Array Slices (Basic, Len, Cap) ***")
	months := [...]string{
		1:  "January",
		2:  "February",
		3:  "March",
		4:  "April",
		5:  "May",
		6:  "June",
		7:  "July",
		8:  "August",
		9:  "September",
		10: "October",
		11: "November",
		12: "December"}

	fmt.Println("months = ", months)
	fmt.Println("cap(months) = ", cap(months))
	fmt.Println("len(months) = ", len(months))

	summer := months[6:9]

	/*

		Slicing beyond cap(s) causes a panic, but slicing beyond len(s) extends
		the slice, so the result may be longer than the original

	*/

	fmt.Println( "*** Slicing Beyond Cap and Len ***")
	fmt.Println("cap(summer) = ", cap(summer))
	fmt.Println("len(summer) = ", len(summer))
	//fmt.Println(summer[:20]) // panic: out of range
	endlessSummer := summer[:5] // extend a slice (within capacity)
	fmt.Println(endlessSummer)  // "[June July August September October]"


}


