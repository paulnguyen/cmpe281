package main

import "fmt"

func main() {

	/*

	   Since a slice contains a pointer to an element of an array, passing a slice to
	   a function permits the function to modify the underlying array elements. In
	   other words, copying a slice creates an alias (§2.3.2) for the underlying
	   array. The function reverse reverses the elements of an []int slice in place,
	   and it may be applied to slices of any length.

	*/

	fmt.Println( "*** Slice Pass by Reference & Modifies Underying Array ***")
	a := [...]int{0, 1, 2, 3, 4, 5}
	reverse(a[:])
	fmt.Println(a) // "[5 4 3 2 1 0]"

	/*

		The built-in append function appends items to slices:

		The loop uses append to build the slice of nine runes encoded by the
		string literal, although this specific problem is more conveniently solved
		by using the built-in conversion

		[]rune("Hello, Image").

	*/

	fmt.Println( "*** Appending to Slices ***")
	var runes []rune
	for _, r := range "Hello, Image" {
		runes = append(runes, r)
	}
	fmt.Printf("%q\n", runes) // "['H' 'e' 'l' 'l' 'o' ',' ' ' 'Image' 'Image']"

	/* appending to slides (see function definition below for "appendInt". */

	var x, y []int
	for i := 0; i < 10; i++ {
		y = appendInt(x, i)
		fmt.Printf("%d  cap=%d\t%v\n", i, cap(y), y)
		x = y
	}

}

// reverse reverses a slice of ints in place.
func reverse(s []int) {
	for i, j := 0, len(s)-1; i < j; i, j = i+1, j-1 {
		s[i], s[j] = s[j], s[i]
	}
}

/*

 The append function is crucial to understanding how slices work, so let’s
 take a look at what is going on. Here’s a version called appendInt that
 is specialized for []int slices:

 Each call to appendInt must check whether the slice has sufficient
 capacity to hold the new elements in the existing array. If so, it
 extends the slice by defining a larger slice (still within the original
 array), copies the element y into the new space, and returns the slice.
 The input x and the result z share the same underlying array.

 If there is insufficient space for growth, appendInt must allocate a new array
 big enough to hold the result, copy the values from x into it, then append the
 new element y. The result z now refers to a different underlying array than
 the array that x refers to.

 The built-in append function may use a more sophisticated growth strategy
 than appendInt’s simplistic one. Usually we don’t know whether a given
 call to append will cause a reallocation, so we can’t assume that the
 original slice refers to the same array as the resulting slice, nor that
 it refers to a different one. Similarly, we must not assume that
 assignments to elements of the old slice will (or will not) be reflected
 in the new slice. Consequently, it’s usual to assign the result of a call
 to append to the same slice variable whose value we passed to append:

 runes = append(runes, r)

*/

func appendInt(x []int, y int) []int {
	var z []int
	zlen := len(x) + 1
	if zlen <= cap(x) {
		// There is room to grow.  Extend the slice.
		z = x[:zlen]
	} else {
		// There is insufficient space.  Allocate a new array.
		// Grow by doubling, for amortized linear complexity.
		zcap := zlen
		if zcap < 2*len(x) {
			zcap = 2 * len(x)
		}
		z = make([]int, zlen, zcap)
		copy(z, x) // a built-in function; see text
	}
	z[len(x)] = y
	return z
}
