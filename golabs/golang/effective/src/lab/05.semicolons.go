package main

/*

	https://golang.org/doc/effective_go.html

	Semicolons:

	Like C, Go's formal grammar uses semicolons to terminate statements, but
	unlike in C, those semicolons do not appear in the source. 

	Instead the lexer uses a simple rule to insert semicolons automatically as
	it scans, so the input text is mostly free of them.

	The rule is this. If the last token before a newline is an identifier
	(which includes words like int and float64), a basic literal such as a
	number or string constant, or one of the tokens

	break continue fallthrough return ++ -- ) }

	The lexer always inserts a semicolon after the token. This could be
	summarized as, “if the newline comes after a token that could end a
	statement, insert a semicolon”.

	A semicolon can also be omitted immediately before a closing brace

*/

import "fmt"

func main() {

	var i int

	// no semicolons needed
	go func() { for { i++ } }()

	// Exit
	fmt.Println( "Go Semicolons:")
	fmt.Println( " ")
	fmt.Println( "If the last token before a newline is an identifier:")
	fmt.Println( "  1. An identifier (which includes words like int and float64)")
	fmt.Println( "  2. A basic literal such as a number or string constant")
	fmt.Println( "  3. Or one of the tokens: ")
	fmt.Println( "     break continue fallthrough return ++ -- ) }")
	fmt.Println( "Then:")
	fmt.Println( "  The lexer always inserts a semicolon after the token")
	fmt.Println( "     ")
	fmt.Println( "Summary:  If the newline comes after a token that could end a statement, insert a semicolon")
	fmt.Println( " ")
}



/*

	Idiomatic Go programs have semicolons only in places such as for loop
	clauses, to separate the initializer, condition, and continuation elements.
	They are also necessary to separate multiple statements on a line, should
	you write code that way.

	One consequence of the semicolon insertion rules is that you cannot put
	the opening brace of a control structure (if, for, switch, or select) on
	the next line. If you do, a semicolon will be inserted before the brace,
	which could cause unwanted effects. 

	Write them like this:

		if i < f() {
		    g()
		}

	Not like this:

		if i < f()  // wrong!
		{           // wrong!
		    g()
		}

*/