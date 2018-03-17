package main

import (
	"encoding/json"
	"fmt"
	"log"
)

func main() {

	/*

		https://www.safaribooksonline.com/library/view/the-go-programming/9780134190570/ebook_split_039.html

		 Go has excellent support for encoding and decoding these formats,
		 provided by the standard library packages encoding/json, encoding/xml,
		 encoding/asn1, and so on, and these packages all have similar APIs. This
		 section gives a brief overview of the most important parts of the
		 encoding/json package.

			boolean            true
			number             -273.15
			string             "She said \"Hello, Image\""
			array              ["gold", "silver", "bronze"]
			object             {"year": 1980,
			                    "event": "archery",
			                    "medals": ["gold", "silver", "bronze"]}

	*/

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

	// Converting a Go data structure like movies to JSON is called
	// marshaling. Marshaling is done by json.Marshal

	fmt.Println("**** JSON marshaling (Go -> JSON) ****")
	data1, err1 := json.Marshal(movies)
	if err1 != nil {
		log.Fatalf("JSON marshaling failed: %s", err1)
	}
	fmt.Printf("%s\n", data1)

	// Marshaling uses the Go struct field names as the field names for the
	// JSON objects (through reflection, as we’ll see in Section 12.6). Only
	// exported fields are marshaled, which is why we chose capitalized names
	// for all the Go field names

	fmt.Println("**** JSON marshaling INDENTED VERSION (Go -> JSON) ****")
	data2, err2 := json.MarshalIndent(movies, "", "    ")
	if err2 != nil {
		log.Fatalf("JSON marshaling failed: %s", err2)
	}
	fmt.Printf("%s\n", data2)

	// You may have noticed that the name of the Year field changed to
	// released in the output, and Color changed to color. That’s because of
	// the field tags. A field tag is a string of metadata associated at
	// compile time with the field of a struct:
	//
	//		Year  int  `json:"released"`
	//		Color bool `json:"color,omitempty"`
	//

	/*
		The json key controls the behavior of the encoding/json package, and
		other encoding/... packages follow this convention. The first part of
		the json field tag specifies an alternative JSON name for the Go field.
		Field tags are often used to specify an idiomatic JSON name like
		total_count for a Go field named TotalCount. The tag for Color has an
		additional option, omitempty, which indicates that no JSON output
		should be produced if the field has the zero value for its type (false,
		here) or is otherwise empty. Sure enough, the JSON output for
		Casablanca, a black-and-white movie, has no color field.

			{
		        "Title": "Casablanca",
		        "released": 1942,
		        "Actors": [
		            "Humphrey Bogart",
		            "Ingrid Bergman"
		        ]
		    },
		    {
		        "Title": "Cool Hand Luke",
		        "released": 1967,
		        "color": true,
		        "Actors": [
		            "Paul Newman"
		        ]
		    },
	*/


}
