package main

/*

	https://golang.org/doc/effective_go.html

	Errors

	Library routines must often return some sort of error indication to the
	caller. As mentioned earlier, Go's multivalue return makes it easy to
	return a detailed error description alongside the normal return value. It
	is good style to use this feature to provide detailed error information.
	For example, as we'll see, os.Open doesn't just return a nil pointer on
	failure, it also returns an error value that describes what went wrong.

	By convention, errors have type error, a simple built-in interface.

	type error interface {
	    Error() string
	}


*/

import "fmt"

func main() {
	fmt.Println("Go Errors")
}

/*


 	A library writer is free to implement this interface with a richer model
 	under the covers, making it possible not only to see the error but also
 	to provide some context. As mentioned, alongside the usual *os.File
 	return value, os.Open also returns an error value. If the file is opened
 	successfully, the error will be nil, but when there is a problem, it will
 	hold an os.PathError:

	// PathError records an error and the operation and
	// file path that caused it.
	type PathError struct {
	    Op string    // "open", "unlink", etc.
	    Path string  // The associated file.
	    Err error    // Returned by the system call.
	}

	func (e *PathError) Error() string {
	    return e.Op + " " + e.Path + ": " + e.Err.Error()
	}


*/



/*

 	Callers that care about the precise error details can use a type switch
 	or a type assertion to look for specific errors and extract details. For
 	PathErrors this might include examining the internal Err field for
 	recoverable failures.

	for try := 0; try < 2; try++ {
	    file, err = os.Create(filename)
	    if err == nil {
	        return
	    }
	    if e, ok := err.(*os.PathError); ok && e.Err == syscall.ENOSPC {
	        deleteTempFiles()  // Recover some space.
	        continue
	    }
	    return
	}

	The second if statement here is another type assertion. If it fails, ok
	will be false, and e will be nil. If it succeeds, ok will be true, which
	means the error was of type *os.PathError, and then so is e, which we can
	examine for more information about the error.

*/



/*

	Panic

	The usual way to report an error to a caller is to return an error as an
	extra return value. The canonical Read method is a well-known instance; it
	returns a byte count and an error. But what if the error is unrecoverable?
	Sometimes the program simply cannot continue.

	For this purpose, there is a built-in function panic that in effect
	creates a run-time error that will stop the program (but see the next
	section). The function takes a single argument of arbitrary type—often a
	string—to be printed as the program dies. It's also a way to indicate that
	something impossible has happened, such as exiting an infinite loop.

	// A toy implementation of cube root using Newton's method.
	func CubeRoot(x float64) float64 {
	    z := x/3   // Arbitrary initial value
	    for i := 0; i < 1e6; i++ {
	        prevz := z
	        z -= (z*z*z-x) / (3*z*z)
	        if veryClose(z, prevz) {
	            return z
	        }
	    }
	    // A million iterations has not converged; something is wrong.
	    panic(fmt.Sprintf("CubeRoot(%g) did not converge", x))
	}

*/



/*

	Recover
	
	When panic is called, including implicitly for run-time errors such as
	indexing a slice out of bounds or failing a type assertion, it immediately
	stops execution of the current function and begins unwinding the stack of
	the goroutine, running any deferred functions along the way. If that
	unwinding reaches the top of the goroutine's stack, the program dies.
	However, it is possible to use the built-in function recover to regain
	control of the goroutine and resume normal execution.

	A call to recover stops the unwinding and returns the argument passed to
	panic. Because the only code that runs while unwinding is inside deferred
	functions, recover is only useful inside deferred functions.

	One application of recover is to shut down a failing goroutine inside a
	server without killing the other executing goroutines.

	func server(workChan <-chan *Work) {
	    for work := range workChan {
	        go safelyDo(work)
	    }
	}

	func safelyDo(work *Work) {
	    defer func() {
	        if err := recover(); err != nil {
	            log.Println("work failed:", err)
	        }
	    }()
	    do(work)
	}

	In this example, if do(work) panics, the result will be logged and the
	goroutine will exit cleanly without disturbing the others. There's no need
	to do anything else in the deferred closure; calling recover handles the
	condition completely.

	Because recover always returns nil unless called directly from a deferred
	function, deferred code can call library routines that themselves use
	panic and recover without failing. As an example, the deferred function in
	safelyDo might call a logging function before calling recover, and that
	logging code would run unaffected by the panicking state.

*/
