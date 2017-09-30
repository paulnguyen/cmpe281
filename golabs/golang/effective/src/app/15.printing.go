package main

/*

	https://golang.org/doc/effective_go.html

	Printing

	Formatted printing in Go uses a style similar to C's printf family but is
	richer and more general. The functions live in the fmt package and have
	capitalized names: fmt.Printf, fmt.Fprintf, fmt.Sprintf and so on. The
	string functions (Sprintf etc.) return a string rather than filling in a
	provided buffer.


*/

import (
	"fmt"
	"os"
)

func main() {

	fmt.Println("Printing in Go")

	// You don't need to provide a format string. For each of Printf, Fprintf
	// and Sprintf there is another pair of functions, for instance Print and
	// Println. These functions do not take a format string but instead
	// generate a default format for each argument. The Println versions also
	// insert a blank between arguments and append a newline to the output
	// while the Print versions add blanks only if the operand on neither side
	// is a string. In this example each line produces the same output.

	fmt.Printf("Hello %d\n", 23)
	fmt.Fprint(os.Stdout, "Hello ", 23, "\n")
	fmt.Println("Hello", 23)
	fmt.Println(fmt.Sprint("Hello ", 23))

	// Here things start to diverge from C. First, the numeric formats such as
	// %d do not take flags for signedness or size; instead, the printing
	// routines use the type of the argument to decide these properties.


	fmt.Println("\nFormatted Printing...")

	var x uint64 = 1<<64 - 1
	fmt.Printf("%d %x\n", x, x )

	// If you just want the default conversion, such as decimal for integers,
	// you can use the catchall format %v (for “value”); the result is exactly
	// what Print and Println would produce. Moreover, that format can print
	// any value, even arrays, slices, structs, and maps. Here is a print
	// statement for the time zone map defined in the previous section.

	fmt.Println("\nDefault Conversion...")

	var timeZone = map[string]int{
	    "UTC":  0*60*60,
	    "EST": -5*60*60,
	    "CST": -6*60*60,
	    "MST": -7*60*60,
	    "PST": -8*60*60,
	}

	fmt.Printf("%v\n", timeZone)  // or just fmt.Println(timeZone)

	// For maps the keys may be output in any order, of course. When printing
	// a struct, the modified format %+v annotates the fields of the structure
	// with their names, and for any value the alternate format %#v prints the
	// value in full Go syntax.

	fmt.Println("\nPrinting Structs...")

	t := &T{ 7, -2.35, "abc\tdef" }
	fmt.Printf("%v\n", t)			// Show Default String Conversion
	fmt.Printf("%+v\n", t)			// Add Struct Fields: %+v
	fmt.Printf("%#v\n", t)			// Full Go Syntax: %#v
	fmt.Printf("%#v\n", timeZone)	// Full Go Syntax: %#v

	// Another handy format is %T, which prints the type of a value. 

	fmt.Println("\nPrinting Type of Value...")
	fmt.Printf("%T\n", timeZone)

	// If you want to control the default format for a custom type, all that's
	// required is to define a method with the signature String() string on
	// the type. For our simple type T, that might look like this.

	fmt.Println("\nDefault String() Function for Types...")
	fmt.Printf("%v\n", t)


}

// Custom Type: T

type T struct {
    a int
    b float64
    c string
}

func (t *T) String() string {
	return fmt.Sprintf("%d/%g/%q", t.a, t.b, t.c)
}
