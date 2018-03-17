// Copyright © 2016 Alan A. A. Donovan & Brian W. Kernighan.
// License: https://creativecommons.org/licenses/by-nc-sa/4.0/

package main

import (
	"fmt"
	"os"
)

func main() {
	var s, sep string
	for i := 1; i < len(os.Args); i++ {
		s += sep + os.Args[i]
		sep = " "
	}
	fmt.Println(s)
}

/*

The for loop is the only loop statement in Go. It has a number of forms
Parentheses are never used around the three components of a for loop.
The braces are mandatory, however, and the opening brace must be on the
same line as the post statement.

	for initialization; condition; post {
		// zero or more statements
	}

Any of these parts may be omitted.  If there is no initialization and nopost,
the semicolons may also be omitted:

	// A traditional "while" loop
	for condition {
		// ...
	}

If the condition is omitted entirely in any of these forms, for example in

	// a traditional infinite loop
	for {
	    // ...
	}

The loop is infinite, though loops of this form may be terminated in some
other way, like a break or return statement.


*/
