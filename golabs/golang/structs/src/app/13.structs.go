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

	fmt.Println("*** Employee Example ***")
	fmt.Println(dilbert)

	/*

		The individual fields of dilbert are accessed using dot notation like
		dilbert.Name and dilbert.DoB. Because dilbert is a variable, its fields
		are variables too, so we may assign to a field

	*/

	dilbert.Salary -= 5000 // demoted, for writing too few lines of code

	/*

	 or take its address and access it through a pointer:

	*/

	position := &dilbert.Position
	*position = "Senior " + *position // promoted, for outsourcing to Elbonia

	/*

		The dot notation also works with a pointer to a struct:

	*/

	var employeeOfTheMonth *Employee = &dilbert
	employeeOfTheMonth.Position = " (proactive team player)"

	// is equivalent to:

	(*employeeOfTheMonth).Position = " (proactive team player)"

	fmt.Println("*** Results of Struct Updates ***")
	fmt.Printf("dilbert: %+v\n", dilbert)

	

}
