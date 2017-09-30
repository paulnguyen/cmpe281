package main

/*

	https://golang.org/doc/effective_go.html

	Append

    Now we have the missing piece we needed to explain the design of the
	append built-in function. The signature of append is different from our custom
	Append function above. Schematically, it's like this:

	func append(slice []T, elements ...T) []T

	where T is a placeholder for any given type. You can't actually write a
	function in Go where the type T is determined by the caller. That's why
	append is built in: it needs support from the compiler.

	


*/

import "fmt"

func main() {
	fmt.Println("Go 'Built-In' Function Append")

	// What append does is append the elements to the end of the slice and
	// return the result.

	x1 := []int{1,2,3}
	x1 = append(x1, 4, 5, 6)
	fmt.Println(x1)

	// But what if we wanted to do what our Append does and append a slice to
	// a slice? Easy: use ... at the call site

	x2 := []int{1,2,3}
	y2 := []int{4,5,6}
	x2 = append(x2, y2...)
	fmt.Println(x2)


}
