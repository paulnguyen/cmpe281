// https://tour.golang.org/basics/10

package main

import "fmt"

func main() {
	var i, j int = 1, 2
	k := 3  // ":=" syntax is short for var delc (but only avail in function)
	c, python, java := true, false, "no!"

	fmt.Println(i, j, k, c, python, java)
}

