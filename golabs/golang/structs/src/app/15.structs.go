package main

import (
	"fmt"
	"time"
)

/*

	https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_038.html

    These two statements declare a struct type called Employee and a
	variable called dilbert that is an instance of an Employee

*/

type Employee struct {
	ID        int
	Name      string
	Address   string
	DoB       time.Time
	Position  string
	Salary    int
	ManagerID int
}

var dilbert Employee

/*

	A named struct type S can’t declare a field of the same type S: an aggregate
	value cannot contain itself. (An analogous restriction applies to arrays.) But
	S may declare a field of the pointer type *S, which lets us create recursive
	data structures like linked lists and trees. This is illustrated in the code
	below, which uses a binary tree to implement an insertion sort:

*/

type tree struct {
	value       int
	left, right *tree
}

/*

	The struct type with no fields is called the empty struct, written struct{}.
	It has size zero and carries no information

*/

type empty struct{}

/*

	Point Struct

*/

type Point struct{ X, Y int }

/**

	MAIN ROUTINE

**/

func main() {


	/*

		If all the fields of a struct are comparable, the struct itself is
		comparable, so two expressions of that type may be compared
		using == or !=.

	*/

	fmt.Println("*** Comparing Structs ***")

	p3 := Point{1, 2}
	q3 := Point{2, 1}
	x3 := Point{2, 1}
	fmt.Println(p3.X == q3.X && p3.Y == q3.Y) // "false"
	fmt.Println(p3 == q3)                     // "false"
	fmt.Println(q3 == x3)                     // "true"

	/*

	 Comparable struct types, like other comparable types, may be used as the
	 key type of a map.

	*/

	type address struct {
		hostname string
		port     int
	}

	hits := make(map[address]int)
	hits[address{"golang.org", 443}]++

	fmt.Println(hits)


}

/*

Struct values can be passed as arguments to functions and returned from them

*/

func Scale(p Point, factor int) Point {
	return Point{p.X * factor, p.Y * factor}
}

/*

For efficiency, larger struct types are usually passed to or returned from
functions indirectly using a pointer.

And this is required if the function must modify its argument, since in a
call-by-value language like Go, the called function receives only a copy of an
argument, not a reference to the original argument.

*/

func Bonus(e *Employee, percent int) int {
	return e.Salary * percent / 100
}
