package main

/*

	https://golang.org/doc/effective_go.html

	Interface

	Interfaces in Go provide a way to specify the behavior of an object: if
	something can do this, then it can be used here. We've seen a couple of
	simple examples already; custom printers can be implemented by a String
	method while Fprintf can generate output to anything with a Write method.
	Interfaces with only one or two methods are common in Go code, and are
	usually given a name derived from the method, such as io.Writer for
	something that implements Write.


*/

import ( 
	"fmt"
	"sort"
)

func main() {
	fmt.Println("Go Interfaces")
	fmt.Println( TypeSwitchTest() )
	TypeAssertionTest()
}

// A type can implement multiple interfaces. For instance, a collection can be
// sorted by the routines in package sort if it implements 
// 
// sort.Interface,
// which contains Len(), Less(i, j int) bool, and Swap(i, j int), 
//
// and it could also have a custom formatter. In this contrived example 
// Sequence satisfies both.

type Sequence []int

// Methods required by sort.Interface.
func (s Sequence) Len() int {
    return len(s)
}
func (s Sequence) Less(i, j int) bool {
    return s[i] < s[j]
}
func (s Sequence) Swap(i, j int) {
    s[i], s[j] = s[j], s[i]
}

// Method for printing - sorts the elements before printing.
func (s Sequence) String() string {
    sort.Sort(s)
    str := "["
    for i, elem := range s {
        if i > 0 {
            str += " "
        }
        str += fmt.Sprint(elem)
    }
    return str + "]"
}

// Conversions:  The String method of Sequence is recreating the work that
// Sprint already does for slices. We can share the effort if we convert the
// Sequence to a plain []int before calling Sprint.

func (s Sequence) String2() string {
    sort.Sort(s)
    return fmt.Sprint([]int(s))
}

// Type switches are a form of conversion: they take an interface and, for
// each case in the switch, in a sense convert it to the type of that case.
// Here's a simplified version of how the code under fmt.Printf turns a value
// into a string using a type switch. If it's already a string, we want the
// actual string value held by the interface, while if it has a String method
// we want the result of calling the method.

type Stringer interface {
	String() string
}

var a Sequence = Sequence {1,2,3,4,5}
var b string = "Hello World"

func TypeSwitchTest() string {

	var value interface{} // Value provided by caller.
	value = a
	//value = b
	switch str := value.(type) {
	case string:
	    return str
	case Stringer:
	    return str.String()
	}
	return ""
}

// A Type assertion takes an interface value and extracts from it a value of
// the specified explicit type. The syntax borrows from the clause opening a
// type switch, but with an explicit type rather than the type keyword:
//
// value.(typeName)
//
// Example:  str := value.(string)  (will crash if value is not a string)
// To fix this, use  "comma, ok" idiom to test whether the value is a string

func TypeAssertionTest() {
	var value interface{} 
	//value = a
	value = b	
	str, ok := value.(string)
	if ok {
	    fmt.Printf("string value is: %q\n", str)
	} else {
	    fmt.Printf("value is not a string\n")
	}
}


// Interface checks - As we saw in the discussion of interfaces above, a type
// need not declare explicitly that it implements an interface. Instead, a
// type implements the interface just by implementing the interface's methods.
// In practice, most interface conversions are static and therefore checked at
// compile time. For example, passing an *os.File to a function expecting an
// io.Reader will not compile unless *os.File implements the io.Reader
// interface.

// Some interface checks do happen at run-time, though. One instance is in the
// encoding/json package, which defines a Marshaler interface. When the JSON
// encoder receives a value that implements that interface, the encoder
// invokes the value's marshaling method to convert it to JSON instead of
// doing the standard conversion. The encoder checks this property at run time
// with a type assertion like:

/*
	m, ok := val.(json.Marshaler)
*/

//  If it's necessary only to ask whether a type implements an interface,
//  without actually using the interface itself, perhaps as part of an error
//  check, use the blank identifier to ignore the type-asserted value:


/*
	if _, ok := val.(json.Marshaler); ok {
	    fmt.Printf("value %v of type %T implements json.Marshaler\n", val, val)
	}
*/






