package main

/*

	https://golang.org/doc/effective_go.html

	For Loop:

	The Go for loop is similar to—but not the same as—C's. It unifies for and
	while and there is no do-while. There are three forms, only one of which
	has semicolons.

		// Like a C for
		for init; condition; post { }

		// Like a C while
		for condition { }

		// Like a C for(;;)
		for { }

	Short declarations make it easy to declare the index variable right in the loop. 

		sum := 0
		for i := 0; i < 10; i++ {
		    sum += i
		}

	If you're looping over an array, slice, string, or map, or reading from a
	channel, a range clause can manage the loop.

		for key, value := range oldMap {
		    newMap[key] = value
		}

	If you only need the first item in the range (the key or index), drop the second: 

		for key := range m {
		    if key.expired() {
		        delete(m, key)
		    }
		}

	If you only need the second item in the range (the value), use the blank
	identifier, an underscore, to discard the first:

		sum := 0
		for _, value := range array {
		    sum += value
		}

	For strings, the range does more work for you, breaking out individual
	Unicode code points by parsing the UTF-8. Erroneous encodings consume one
	byte and produce the replacement rune U+FFFD. (The name (with associated
	builtin type) rune is Go terminology for a single Unicode code point. See
	the language specification for details.) 

*/

import "fmt"

func main() {

	fmt.Println( "Go Rune = Single Unicode code point (For Loop / Range Example):")

	for pos, char := range "日本\x80語" { // \x80 is an illegal UTF-8 encoding
	    fmt.Printf("character %#U starts at byte position %d\n", char, pos)
	}

	fmt.Println( "========================================================")

	fmt.Println("Go has no comma operator and ++ and -- are statements not expressions.")
	fmt.Println("Thus if you want to run multiple variables in a for you should use parallel")
	fmt.Println("assignment (although that precludes ++ and --). ")

	// Reverse a
	a := [10] int { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }
	fmt.Println( "Before Reverse: ", a )
	for i, j := 0, len(a)-1; i < j; i, j = i+1, j-1 {
	    a[i], a[j] = a[j], a[i]
	}
	fmt.Println( "After Reverse:  ", a )


}
