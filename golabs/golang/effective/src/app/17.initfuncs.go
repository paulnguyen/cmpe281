package main

/*

	https://golang.org/doc/effective_go.html

	Initialization

	Although it doesn't look superficially very different from initialization
	in C or C++, initialization in Go is more powerful. Complex structures can
	be built during initialization and the ordering issues among initialized
	objects, even among different packages, are handled correctly.

*/

import (
	"fmt"
	"os"
	"log"
)

func main() {

	fmt.Println("Go Initialization")

	// Constants - Constants in Go are just that—constant. They are created at
	// compile time, even when defined as locals in functions, and can only be
	// numbers, characters (runes), strings or booleans.
	//
	// iota - https://splice.com/blog/iota-elegant-constants-golang/

	const (
	    XCategoryBooks    = 0
	    XCategoryHealth   = 1
	    XCategoryClothing = 2	
	)

	const (
	    YCategoryBooks = iota // 0
	    YCategoryHealth       // 1
	    YCategoryClothing     // 2
	)


	fmt.Println( home, user, gopath )

}



// The init function - Finally, each source file can define its own niladic
// init function to set up whatever state is required. (Actually each file can
// have multiple init functions.) And finally means finally: init is called
// after all the variable declarations in the package have evaluated their
// initializers, and those are evaluated only after all the imported packages
// have been initialized.

func init() {
    if user == "" {
        log.Fatal("$USER not set")
    }
    if home == "" {
        home = "/home/" + user
    }
}

// Variables can be initialized just like constants but the initializer
// can be a general expression computed at run time.

var (
    home   = os.Getenv("HOME")
    user   = os.Getenv("USER")
    gopath = os.Getenv("GOPATH")
)

// The ability to attach a method such as String to any user-defined type
// makes it possible for arbitrary values to format themselves
// automatically for printing. Although you'll see it most often applied
// to structs, this technique is also useful for scalar types such as
// floating-point types like ByteSize.

type ByteSize float64
const (
    _           = iota // ignore first value by assigning to blank identifier
    KB ByteSize = 1 << (10 * iota)
    MB
    GB
    TB
    PB
    EB
    ZB
    YB
)

func (b ByteSize) String() string {
    switch {
    case b >= YB:
        return fmt.Sprintf("%.2fYB", b/YB)
    case b >= ZB:
        return fmt.Sprintf("%.2fZB", b/ZB)
    case b >= EB:
        return fmt.Sprintf("%.2fEB", b/EB)
    case b >= PB:
        return fmt.Sprintf("%.2fPB", b/PB)
    case b >= TB:
        return fmt.Sprintf("%.2fTB", b/TB)
    case b >= GB:
        return fmt.Sprintf("%.2fGB", b/GB)
    case b >= MB:
        return fmt.Sprintf("%.2fMB", b/MB)
    case b >= KB:
        return fmt.Sprintf("%.2fKB", b/KB)
    }
    return fmt.Sprintf("%.2fB", b)
}
