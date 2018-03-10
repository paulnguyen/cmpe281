package main

import (
	"encoding/json"
	"fmt"
	"log"
)

func main() {

	//  Consider an application that gathers movie reviews and offers
	//  recommendations. Its Movie data type and a typical list of values
	//  are declared below. (The string literals after the Year and Color
	//  field declarations are field tags; we’ll explain them in a moment.)

	type Movie struct {
		Title  string
		Year   int  `json:"released"`
		Color  bool `json:"color,omitempty"`
		Actors []string
	}

	var movies = []Movie{
		{Title: "Casablanca", Year: 1942, Color: false,
			Actors: []string{"Humphrey Bogart", "Ingrid Bergman"}},
		{Title: "Cool Hand Luke", Year: 1967, Color: true,
			Actors: []string{"Paul Newman"}},
		{Title: "Bullitt", Year: 1968, Color: true,
			Actors: []string{"Steve McQueen", "Jacqueline Bisset"}},
		// ...
	}

	/*

		The inverse operation to marshaling, decoding JSON and populating a Go
		data structure, is called unmarshaling, and it is done by
		json.Unmarshal. The code below unmarshals the JSON movie data into a
		slice of structs whose only field is Title. By defining suitable Go
		data structures in this way, we can select which parts of the JSON
		input to decode and which to discard. When Unmarshal returns, it has
		filled in the slice with the Title information; other names in the
		JSON are ignored.

	*/

	fmt.Println("**** JSON Marshaling (JSON -> Go) ****")
	data, err := json.Marshal(movies)
	if err != nil {
		log.Fatalf("JSON marshaling failed: %s", err)
	}

	var titles []struct{ Title string }

	if err := json.Unmarshal(data, &titles); err != nil {
		log.Fatalf("JSON unmarshaling failed: %s", err)
	}
	fmt.Println(titles) // "[{Casablanca} {Cool Hand Luke} {Bullitt}]"

}
