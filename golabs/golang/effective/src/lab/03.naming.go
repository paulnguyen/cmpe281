package main

/*

	https://golang.org/doc/effective_go.html

	Naming:

	============	
	Package Name
	============

 	When a package is imported, the package name becomes an accessor for the
 	contents. After

	import "bytes"

	The importing package can talk about bytes.Buffer...

	Another convention is that the package name is the base name of its source
	directory; the package in src/encoding/base64 is imported as
	"encoding/base64" but has name base64, not encoding_base64 and not
	encodingBase64.

	Imported entities are always addressed with their package name,
	bufio.Reader does not conflict with io.Reader.


	===============
	Interface Names
	===============

	By convention, one-method interfaces are named by the method name plus an
	-er suffix or similar modification to construct an agent noun: Reader,
	Writer, Formatter, CloseNotifier etc.

	There are a number of such names and it's productive to honor them and the
	function names they capture. Read, Write, Close, Flush, String and so on
	have canonical signatures and meanings. To avoid confusion, don't give
	your method one of those names unless it has the same signature and
	meaning. Conversely, if your type implements a method with the same
	meaning as a method on a well-known type, give it the same name and
	signature; call your string-converter method String not ToString.

	=========
	MixedCaps
	=========

	Finally, the convention in Go is to use MixedCaps or mixedCaps rather than
	underscores to write multiword names.

*/

import "fmt"

func main() {
	fmt.Println( "Go Naming Conventions")
	fmt.Println( "1. Package name becomes an accessor for the contents... import \"bytes\" --> bytes.Buffer")
	fmt.Println( "2. One-method interfaces are named by the method name plus an -er suffix... Reader, Writer, Etc...")
	fmt.Println( "3. MixedCaps Naming rather than underscores... MixedCaps or mixedCaps not mixed_caps or MIXED_CAPS.")
}
