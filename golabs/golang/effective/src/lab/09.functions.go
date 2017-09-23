package main

/*

	https://golang.org/doc/effective_go.html

*/

import (
	"fmt"
	"os"
	"io"
)

func main() {
	fmt.Println("Go Functions")
	fmt.Println( "=== Defer Test ===")
	deferTest()
	fmt.Println( "=== Trace Test 1 ===")
	traceExample1()
	fmt.Println( "=== Trace Test 2 ===")
	traceExample2()
}

/*

	Multiple return values

	One of Go's unusual features is that functions and methods can return
	multiple values. This form can be used to improve on a couple of clumsy
	idioms in C programs: in-band error returns such as -1 for EOF and
	modifying an argument passed by address.

*/

func isDigit( b byte ) bool {
	return true
}

func nextInt(b []byte, i int) (int, int) {
    for ; i < len(b) && !isDigit(b[i]); i++ {
    }
    x := 0
    for ; i < len(b) && isDigit(b[i]); i++ {
        x = x*10 + int(b[i]) - '0'
    }
    return x, i
}


/*

	Named result parameters

	The return or result "parameters" of a Go function can be given names and
	used as regular variables, just like the incoming parameters. When named,
	they are initialized to the zero values for their types when the function
	begins; if the function executes a return statement with no arguments, the
	current values of the result parameters are used as the returned values.

	The names are not mandatory but they can make code shorter and clearer:
	they're documentation. If we name the results of nextInt it becomes
	obvious which returned int is which.

*/

type Reader struct {
	s        []byte
	i        int64 // current reading index
	prevRune int   // index of previous rune; or < 0
}

func (r Reader) Read([]byte) (int, int) {
    return 1, 1
}

func ReadFull(r Reader, buf []byte) (n int, err int) {
    for len(buf) > 0 && err == 0 {
        var nr int
        nr, err = r.Read(buf)
        n += nr
        buf = buf[nr:]
    }
    return  // named results are initialized and tied to an unadorned return
}


/*

	Defer

	Go's defer statement schedules a function call (the deferred function) to
	be run immediately before the function executing the defer returns. It's
	an unusual but effective way to deal with situations such as resources
	that must be released regardless of which path a function takes to return.
	The canonical examples are unlocking a mutex or closing a file.

*/

// Contents returns the file's contents as a string.

func Contents(filename string) (string, error) {
    f, err := os.Open(filename)
    if err != nil {
        return "", err
    }
    defer f.Close()  // f.Close will run when we're finished.

    var result []byte
    buf := make([]byte, 100)
    for {
        n, err := f.Read(buf[0:])
        result = append(result, buf[0:n]...) // append is discussed later.
        if err != nil {
            if err == io.EOF {
                break
            }
            return "", err  // f will be closed if we return here.
        }
    }
    return string(result), nil // f will be closed if we return here.
}	


/*
	
	The arguments to the deferred function (which include the receiver if the
	function is a method) are evaluated when the defer executes, not when the
	call executes. Besides avoiding worries about variables changing values as
	the function executes, this means that a single deferred call site can
	defer multiple function executions. Here's a silly example.

*/

func deferTest() {
	defer fmt.Println("")
	for i := 0; i < 5; i++ {
	    defer fmt.Printf("%d ", i) // Deferred functions are executed in LIFO order,
	}	
}


/*
	
	A more plausible example is a simple way to trace function execution
	through the program. We could write a couple of simple tracing routines
	like this:

*/

func trace1(s string)   { fmt.Println("entering:", s) }
func untrace1(s string) { fmt.Println("leaving:", s) }

// Use them like this:
func traceExample1() {
    trace1("traceExample1")
    defer untrace1("traceExample1")
    // do something....
}


/*

	We can do better by exploiting the fact that arguments to deferred
	functions are evaluated when the defer executes. The tracing routine can
	set up the argument to the untracing routine. This example:

*/

func trace2(s string) string {
    fmt.Println("entering:", s)
    return s
}

func untrace2(s string) {
    fmt.Println("leaving:", s)
}

func a() {
    defer untrace2(trace2("a"))
    fmt.Println("in a")
}

func b() {
    defer untrace2(trace2("b"))
    fmt.Println("in b")
    a()
}

func traceExample2() {
    b()
}




