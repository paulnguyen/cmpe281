package main

/*

	https://golang.org/doc/effective_go.html

*/

import "fmt"

func main() {
	fmt.Println("Go Switch")
}


/*

	Go's switch is more general than C's. The expressions need not be
	constants or even integers, the cases are evaluated top to bottom until a
	match is found, and if the switch has no expression it switches on true.
	It's therefore possible—and idiomatic—to write an if-else-if-else chain as
	a switch.

*/

func unhex(c byte) byte {
    switch {
    case '0' <= c && c <= '9':
        return c - '0'
    case 'a' <= c && c <= 'f':
        return c - 'a' + 10
    case 'A' <= c && c <= 'F':
        return c - 'A' + 10
    }
    return 0
}


/*

	There is no automatic fall through, but cases can be presented in comma-
	separated lists.

*/

func shouldEscape(c byte) bool {
    switch c {
    case ' ', '?', '&', '=', '#', '+', '%':
        return true
    }
    return false
}


/*

	A switch can also be used to discover the dynamic type of an interface
	variable. Such a type switch uses the syntax of a type assertion with the
	keyword type inside the parentheses. If the switch declares a variable in
	the expression, the variable will have the corresponding type in each
	clause. It's also idiomatic to reuse the name in such cases, in effect
	declaring a new variable with the same name but a different type in each
	case.

*/

func functionOfSomeType() bool {
	return true
}

func typeSwitch() {

	var t interface{}
	t = functionOfSomeType()
	switch t := t.(type) {
	default:
	    fmt.Printf("unexpected type %T\n", t)     // %T prints whatever type t has
	case bool:
	    fmt.Printf("boolean %t\n", t)             // t has type bool
	case int:
	    fmt.Printf("integer %d\n", t)             // t has type int
	case *bool:
	    fmt.Printf("pointer to boolean %t\n", *t) // t has type *bool
	case *int:
	    fmt.Printf("pointer to integer %d\n", *t) // t has type *int
	}	

}


