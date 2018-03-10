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

	/*

	   The slice operator s[i:j], where 0 ≤ i ≤ j ≤ cap(s), creates a new slice that
	   refers to elements i through j-1 of the sequence s, which may be an array
	   variable, a pointer to an array, or another slice. The resulting slice has j-i
	   elements. If i is omitted, it’s 0, and if j is omitted, it’s len(s). Thus the
	   slice months[1:13] refers to the whole range of valid months, as does the
	   slice months[1:]; the slice months[:] refers to the whole array.

	*/

	fmt.Println( "*** Slice Example (Months) ***")
	Q2 := months[4:7]
	summer := months[6:9]
	fmt.Println("Q2 (months[4:7]) = ", Q2)         // ["April" "May" "June"]
	fmt.Println("Summer (months[6:9]) = ", summer) // ["June" "July" "August"]


}

