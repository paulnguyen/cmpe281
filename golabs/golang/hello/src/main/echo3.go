// Copyright © 2016 Alan A. A. Donovan & Brian W. Kernighan.
// License: https://creativecommons.org/licenses/by-nc-sa/4.0/

package main

import (
	"fmt"
	"os"
	"strings"
	"time"
)

func main() {
	t1 := time.Now()
	s := strings.Join(os.Args[1:11], "-")
	t2 := time.Since(t1)
	fmt.Println(s)
	fmt.Println(t2)
}

/*

A simpler and more efficient solution would be to use the Join function
from the strings package:

	func main() {
    	fmt.Println(strings.Join(os.Args[1:], " "))
	}

 */


