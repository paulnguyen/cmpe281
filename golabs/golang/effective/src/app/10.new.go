package main

/*

	https://golang.org/doc/effective_go.html

	Go has two allocation primitives, the built-in functions new and make.
	They do different things and apply to different types, which can be
	confusing, but the rules are simple. Let's talk about new first. It's a
	built-in function that allocates memory, but unlike its namesakes in
	some other languages it does not initialize the memory, it only zeros
	it. That is, new(T) allocates zeroed storage for a new item of type T
	and returns its address, a value of type *T. In Go terminology, it
	returns a pointer to a newly allocated zero value of type T.

*/

import (
	"fmt"
)

const (
   red = 1
   blue = 2
   green = 3
   yello = 4
)

type GumballMachine struct {
	Count       int
	Gumballs 	[]int
	Coins 		map[string]int
}

func main() {

	fmt.Println( "===== setup array and slices =====")
	gumballs_array_literal := [7]int{red, blue, blue, green, yello, red, red}
	fmt.Println( "gumballs_array_literal = ", gumballs_array_literal, 
				 " len = ", len(gumballs_array_literal), 
				 " cap = ", cap(gumballs_array_literal) )
	gumballs_slice_literal := []int{red, blue, blue, green, yello, red, red}
	fmt.Println( "gumballs_slice_literal = ", gumballs_slice_literal, 
				 " len = ", len(gumballs_slice_literal), 
				 " cap = ", cap(gumballs_slice_literal)  )
	gumballs_slice_variable := make([]int,0,100) // make a new slice -- initial = 0, capacity = 100
	fmt.Println( "gumballs_slice_variable (initial) = ", gumballs_slice_variable, 
				 " len = ", len(gumballs_slice_variable), 
				 " cap = ", cap(gumballs_slice_variable) )
	gumballs_slice_variable = append(gumballs_slice_variable, red)
	gumballs_slice_variable = append(gumballs_slice_variable, blue)
	gumballs_slice_variable = append(gumballs_slice_variable, blue)
	gumballs_slice_variable = append(gumballs_slice_variable, green)
	gumballs_slice_variable = append(gumballs_slice_variable, yello)
	fmt.Println( "gumballs_slice_variable (loaded) = ", gumballs_slice_variable, 
				 " len = ", len(gumballs_slice_variable), 
				 " cap = ", cap(gumballs_slice_variable)  )
 

	// New is a built-in function that allocates memory, but unlike its namesakes in
	// some other languages it does not initialize the memory, it only zeros
	// it. That is, new(T) allocates zeroed storage for a new item of type T
	// and returns its address, a value of type *T. In Go terminology, it
	// returns a pointer to a newly allocated zero value of type T.
	fmt.Println( "===== struct initialization with array literal =====")
	ga := GumballMachine {
		Count: 		len(gumballs_array_literal),
		Gumballs: 	gumballs_array_literal[:],  // slice of backing array
		Coins: 		map[string]int { "25 cents": 25, "10 cents": 10, "1 cent": 1},
	}
	fmt.Println( ga )
	fmt.Println( &ga )

	fmt.Println( "===== struct initialization with slice variable =====")
	gb := GumballMachine {
		Count: 		0,
		Gumballs: 	nil,  
		Coins: 		map[string]int { "25 cents": 25, "10 cents": 10, "1 cent": 1},
	}
	gb.Gumballs = gumballs_slice_variable
	gb.Count = len(gumballs_slice_variable)
	fmt.Println( gb )
	fmt.Println( &gb )


	fmt.Println( "===== Allocate a new Struct =====")
	var g0 *GumballMachine = new(GumballMachine)
	fmt.Println( " g0 = ", g0 )
	fmt.Println( "*g0 = ", *g0 )

	// Sometimes the zero value isn't good enough and an initializing constructor is necessary
	fmt.Println( "===== Allocate a new Struct Using Constructor (version 1) =====")
	var g1 *GumballMachine = NewGumballMachine1(gumballs_array_literal)
	g1.Gumballs[0] = -1
	fmt.Println( " g1 = ", g1 )
	fmt.Println( "*g1 = ", *g1 )


	// There's a lot of boiler plate in there. We can simplify it using a
	// composite literal, which is an expression that creates a new instance
	// each time it is evaluated.
	fmt.Println( "===== Allocate a new Struct Using Constructor (version 2) =====")
	var g2 *GumballMachine = NewGumballMachine2(gumballs_array_literal)
	fmt.Println( " g2 = ", g2 )
	fmt.Println( "*g2 = ", *g2 )

	// Taking the address of a composite literal allocates a fresh
	// instance each time it is evaluated, so we can combine these two lines...
	fmt.Println( "===== Allocate a new Struct Using Constructor (version 3) =====")
	var g3 *GumballMachine = NewGumballMachine3(gumballs_array_literal)
	fmt.Println( " g3 = ", g3 )
	fmt.Println( "*g3 = ", *g3 )

}


// Sometimes the zero value isn't good enough and an initializing constructor is necessary
func NewGumballMachine1(array [7]int) *GumballMachine {
	var gm *GumballMachine = new(GumballMachine)
	gm.Count = len(array)
	gm.Gumballs = array[:]
	gm.Coins = map[string]int { "25 cents": 25, "10 cents": 10, "1 cent": 1}
	return gm
}

// There's a lot of boiler plate in there. We can simplify it using a
// composite literal, which is an expression that creates a new instance
// each time it is evaluated.
func NewGumballMachine2(array [7]int) *GumballMachine {

	gm := GumballMachine {
		Count: 		len(array),
		Gumballs: 	array[:],  // slice of backing array
		Coins: 		map[string]int { "25 cents": 25, "10 cents": 10, "1 cent": 1},
	}

	// Note that, unlike in C, it's perfectly OK to return the address of a
	// local variable; the storage associated with the variable survives after
	// the function returns
	return &gm 
}

// In fact, taking the address of a composite literal allocates a fresh
// instance each time it is evaluated, so we can combine these two lines...
func NewGumballMachine3(array [7]int) *GumballMachine {
	return &GumballMachine {
		Count: 		len(array),
		Gumballs: 	array[:],  // slice of backing array
		Coins: 		map[string]int { "25 cents": 25, "10 cents": 10, "1 cent": 1},
	}
}

