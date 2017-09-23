package main

/*

	https://golang.org/doc/effective_go.html

	The blank identifier

	We've mentioned the blank identifier a couple of times now, in the context
	of for range loops and maps. The blank identifier can be assigned or
	declared with any value of any type, with the value discarded harmlessly.
	It's a bit like writing to the Unix /dev/null file: it represents a write-
	only value to be used as a place-holder where a variable is needed but the
	actual value is irrelevant. It has uses beyond those we've seen already.


*/


import (
    "fmt"
    "io"
    "log"
    "os"
)

// If an assignment requires multiple values on the left side, but one of the
// values will not be used by the program, a blank identifier on the left-
// hand-side of the assignment avoids the need to create a dummy variable and
// makes it clear that the value is to be discarded.

/*

	if _, err := os.Stat(path); os.IsNotExist(err) {
		fmt.Printf("%s does not exist\n", path)
	}

*/


// Unused imports and variables - It is an error to import a package or to
// declare a variable without using it. Unused imports bloat the program and
// slow compilation, while a variable that is initialized but not used is at
// least a wasted computation and perhaps indicative of a larger bug. When a
// program is under active development, however, unused imports and variables
// often arise and it can be annoying to delete them just to have the
// compilation proceed, only to have them be needed again later. The blank
// identifier provides a workaround.


var _ = fmt.Printf // For debugging; delete when done.
var _ io.Reader    // For debugging; delete when done.

func main() {
	fmt.Println("The Blank Identifier")
    fd, err := os.Open("21.blankidentifier.go")
    if err != nil {
        log.Fatal(err)
    }
    // TODO: use fd.
    _ = fd	
}


// Import for side effect - An unused import like fmt or io in the previous
// example should eventually be used or removed: blank assignments identify
// code as a work in progress. But sometimes it is useful to import a package
// only for its side effects, without any explicit use. For example, to use
// its init function...

/*
   import _ "net/http/pprof"
*/






