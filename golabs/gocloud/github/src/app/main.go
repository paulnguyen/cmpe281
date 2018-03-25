// Copyright © 2016 Alan A. A. Donovan & Brian W. Kernighan.
// License: https://creativecommons.org/licenses/by-nc-sa/4.0/

// See page 112.
//!+

// Issues prints a table of GitHub issues matching the search terms.
package main

import (
	"fmt"
	"log"
	"os"
	"text/template"
	"time"	
)

//!+template
const templ = `{{.TotalCount}} issues:
{{range .Items}}----------------------------------------
Number: {{.Number}}
User:   {{.User.Login}}
Title:  {{.Title | printf "%.64s"}}
Age:    {{.CreatedAt | daysAgo}} days
{{end}}`

//!-template

//!+daysAgo
func daysAgo(t time.Time) int {
	return int(time.Since(t).Hours() / 24)
}

//!-daysAgo

//!+exec
var report = template.Must(template.New("issuelist").
	Funcs(template.FuncMap{"daysAgo": daysAgo}).
	Parse(templ))


//!+
func main() {

	result, err := SearchIssues(os.Args[1:])
	if err != nil {
		log.Fatal(err)
	}

	fmt.Println( "\n\n**** REPORT (RAW DUMP) ****")
	fmt.Printf("%d issues:\n", result.TotalCount)
	for _, item := range result.Items {
		fmt.Printf("#%-5d %9.9s %.55s\n",
			item.Number, item.User.Login, item.Title)
	}


	/*
 		A template is a string or file containing one or more portions
 		enclosed in double braces, {{...}}, called actions. Most of the
 		string is printed literally, but the actions trigger other behaviors.
 		Each action contains an expression in the template language, a simple
 		but powerful notation for printing values, selecting struct fields,
 		calling functions and methods, expressing control flow such as if-
 		else statements and range loops, and instantiating other templates. A
 		simple template string is shown below:

 		const templ = `{{.TotalCount}} issues:
		{{range .Items}}----------------------------------------
		Number: {{.Number}}
		User:   {{.User.Login}}
		Title:  {{.Title | printf "%.64s"}}
		Age:    {{.CreatedAt | daysAgo}} days
		{{end}}`

 		This template first prints the number of matching issues, then prints
 		the number, user, title, and age in days of each one. Within an
 		action, there is a notion of the current value, referred to as “dot”
 		and written as “.”, a period. The dot initially refers to the
 		template’s parameter, which will be a github.IssuesSearchResult in
 		this example. The {{.TotalCount}} action expands to the value of the
 		TotalCount field, printed in the usual way. The {{range .Items}} and
 		{{end}} actions create a loop, so the text between them is expanded
 		multiple times, with dot bound to successive elements of Items.

		Within an action, the | notation makes the result of one operation the
		argument of another, analogous to a Unix shell pipeline. In the case
		of Title, the second operation is the printf function, which is a
		built-in synonym for fmt.Sprintf in all templates. For Age, the second
		operation is the following function, daysAgo, which converts the
		CreatedAt field into an elapsed time, using time.Since:

		Producing output with a template is a two-step process. First we must
		parse the template into a suitable internal representation, and then
		execute it on specific inputs. Parsing need be done only once. The
		code below creates and parses the template templ defined above. Note
		the chaining of method calls: template.New creates and returns a
		template; Funcs adds daysAgo to the set of functions accessible within
		this template, then returns that template; finally, Parse is called on
		the result.

		report, err := template.New("report").
    	Funcs(template.FuncMap{"daysAgo": daysAgo}).
    	Parse(templ)
		if err != nil {
    		log.Fatal(err)
		}
	*/

	fmt.Println( "\n\n**** REPORT (FORMATED) ****")
	result2, err2 := SearchIssues(os.Args[1:])
	if err2 != nil {
		log.Fatal(err2)
	}
	if err := report.Execute(os.Stdout, result2); err != nil {
		log.Fatal(err)
	}

}

//!-

/*
//!+textoutput
$ go build gopl.io/ch4/issues
$ ./issues repo:golang/go is:open json decoder
13 issues:
#5680    eaigner encoding/json: set key converter on en/decoder
#6050  gopherbot encoding/json: provide tokenizer
#8658  gopherbot encoding/json: use bufio
#8462  kortschak encoding/json: UnmarshalText confuses json.Unmarshal
#5901        rsc encoding/json: allow override type marshaling
#9812  klauspost encoding/json: string tag not symmetric
#7872  extempora encoding/json: Encoder internally buffers full output
#9650    cespare encoding/json: Decoding gives errPhase when unmarshalin
#6716  gopherbot encoding/json: include field name in unmarshal error me
#6901  lukescott encoding/json, encoding/xml: option to treat unknown fi
#6384    joeshaw encoding/json: encode precise floating point integers u
#6647    btracey x/tools/cmd/godoc: display type kind of each named type
#4237  gjemiller encoding/base64: URLEncoding padding is optional
//!-textoutput
*/


/*
//!+output
$ go build gopl.io/ch4/issuesreport
$ ./issuesreport repo:golang/go is:open json decoder
13 issues:
----------------------------------------
Number: 5680
User:   eaigner
Title:  encoding/json: set key converter on en/decoder
Age:    750 days
----------------------------------------
Number: 6050
User:   gopherbot
Title:  encoding/json: provide tokenizer
Age:    695 days
----------------------------------------
...
//!-output
*/

