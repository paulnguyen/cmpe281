// Copyright © 2016 Alan A. A. Donovan & Brian W. Kernighan.
// License: https://creativecommons.org/licenses/by-nc-sa/4.0/

package main

import (
	"fmt"
	"os"
)

func main() {
	fmt.Println(os.Args[0])  // the name of the command that was invoked
	fmt.Println(os.Args[1:])
}

/*

 Finally, if we don’t care about format but just want to see the values,
 perhaps for debugging, we can let Println format the results for us:

 	fmt.Println(os.Args[1:])

 */
