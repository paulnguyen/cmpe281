package main

/*

	https://golang.org/doc/effective_go.html

	Go is a new language. Although it borrows ideas from existing languages, it
	has unusual properties that make effective Go programs different in character
	from programs written in its relatives. A straightforward translation of a C++
	or Java program into Go is unlikely to produce a satisfactory result—Java
	programs are written in Java, not Go. On the other hand, thinking about the
	problem from a Go perspective could produce a successful but quite different
	program. In other words, to write Go well, it's important to understand its
	properties and idioms. It's also important to know the established conventions
	for programming in Go, such as naming, formatting, program construction, and
	so on, so that programs you write will be easy for other Go programmers to
	understand.

	This document gives tips for writing clear, idiomatic Go code. It augments the
	language specification, the Tour of Go, and How to Write Go Code, all of which
	you should read first.

*/

import "fmt"

func main() {
	fmt.Println("Hello Effective Go")
}
