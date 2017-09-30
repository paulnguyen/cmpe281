package main

/*

	https://golang.org/doc/effective_go.html

	Redeclaration and reassignment:

	In a := declaration a variable v may appear even if it has already been declared, provided:

    this declaration is in the same scope as the existing declaration of v (if
    v is already declared in an outer scope, the declaration will create a new
    variable §), the corresponding value in the initialization is assignable
    to v, and there is at least one other variable in the declaration that is
    being declared anew.

	This unusual property is pure pragmatism, making it easy to use a single
	err value, for example, in a long if-else chain. You'll see it used often.

*/

import "fmt"

func main() {
	fmt.Println( "Redeclaration and reassignment:" )
	fmt.Println( "In a := declaration a variable v may appear even if it has already been declared" )
}
