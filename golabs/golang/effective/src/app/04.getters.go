package main

/*

	https://golang.org/doc/effective_go.html

	Getters:

 	Go doesn't provide automatic support for getters and setters. There's
 	nothing wrong with providing getters and setters yourself, and it's often
 	appropriate to do so, but it's neither idiomatic nor necessary to put Get
 	into the getter's name. If you have a field called owner (lower case,
 	unexported), the getter method should be called Owner (upper case,
 	exported), not GetOwner. The use of upper-case names for export provides
 	the hook to discriminate the field from the method. A setter function, if
 	needed, will likely be called SetOwner. Both names read well in practice:

	owner := obj.Owner()
	if owner != user {
	    obj.SetOwner(user)
	}


*/

import "fmt"

func main() {
	fmt.Println( "Go Getters and Setters")
	p := new(Person)
	p.SetName("John Smith")
	name := p.Name()
	fmt.Println(name)
}

type Person struct {
	name string
}

func (f *Person) SetName(name string) {
	f.name = name
}

func (f *Person) Name() string {
	return f.name
}
