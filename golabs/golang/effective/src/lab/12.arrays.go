package main

/*

	https://golang.org/doc/effective_go.html

	Arrays are useful when planning the detailed layout of memory and
	sometimes can help avoid allocation, but primarily they are a building
	block for slices.

 	There are major differences between the ways arrays work in Go and C. 

 	In Go:

    1. Arrays are values. Assigning one array to another copies all the
       elements. 

    2. In particular, if you pass an array to a function, it will receive a
       copy of the array, not a pointer to it. 

    3. The size of an array is part of its type. The types [10]int and [20]int
       are distinct.

	The value property can be useful but also expensive; if you want C-like
	behavior and efficiency, you can pass a pointer to the array.

*/

import "fmt"

func main() {
	fmt.Println("Go Arrays")

	array := [...]float64{7.0, 8.5, 9.1}
	x := Sum(&array)  // Note the explicit address-of operator

}

func Sum(a *[3]float64) (sum float64) {
    for _, v := range *a {
        sum += v
    }
    return
}

