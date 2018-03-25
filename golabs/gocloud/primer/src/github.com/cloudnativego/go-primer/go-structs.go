
/*

Structs in Go are just typed collections of fields. We have a tremendous
amount of flexibility with structs. 

	- We can nest them
	- We can create anonymous structs
	- We can create methods that operate on structs.

*/

package main

import (
	"fmt"
	"math"
)

type power struct {
	attack  int
	defense int
}

type location struct {
	x float64
	y float64
	z float64
}

type nonPlayerCharacter struct {
	name  string
	speed int
	hp    int
	power power
	loc   location
}

func (loc location) String() string {
	return fmt.Sprintf("(%f,%f,%f)", loc.x, loc.y, loc.z)
}

func (loc location) euclideanDistance(target location) float64 {
	return math.Sqrt(
		(loc.x-target.x)*(loc.x-target.x) +
			(loc.y-target.y)*(loc.y-target.y) +
			(loc.z-target.z)*(loc.z-target.z))
}

func (npc nonPlayerCharacter) distanceTo(target nonPlayerCharacter) float64 {
	return npc.loc.euclideanDistance(target.loc)
}

func (npc nonPlayerCharacter) String() string {
	return fmt.Sprintf("%s %s", npc.name, npc.loc)
}

func main() {
	fmt.Println("Structs...")

	demon := nonPlayerCharacter{
		name:  "Alfred",
		speed: 21,
		hp:    1000,
		power: power{attack: 75, defense: 50},
		loc:   location{x: 1, y: 1, z: 1},
	}

	fmt.Println(demon)

	anotherDemon := nonPlayerCharacter{
		name:  "Beelzebub",
		speed: 30,
		hp:    5000,
		power: power{attack: 10, defense: 10},
		loc:   location{x: 5, y: 5, z: 5},
	}

	fmt.Println(anotherDemon)

	fmt.Printf("Npc %v is %f units away from Npc %v\n", demon, demon.distanceTo(anotherDemon), anotherDemon)
}
