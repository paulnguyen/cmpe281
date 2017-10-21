package main

/*

	REF:  https://golang.org/pkg/net/http/

*/

import (
	"fmt"
	"io/ioutil"
	"strings"
	"encoding/json"
	"net/http"
	//"net/url"
	//"strconv"
	//b64 "encoding/base64"
)

type gumballMachine struct {
	Id            int
	CountGumballs int
	ModelNumber   string
	SerialNumber  string
}

type order struct {
	Id          string
	OrderStatus string
}


func main() {

	fmt.Println("\n===== Get Gumball Machine Information =====")
	resp, err := http.Get("http://localhost:3000/gumball")
	defer resp.Body.Close()
	if err != nil {
		fmt.Println(err.Error())
	} else {
		body, _ := ioutil.ReadAll(resp.Body)
		//fmt.Println(string(body))
		fmt.Println( "Unmarshaling JSON Response...")
		var g gumballMachine
		if err := json.Unmarshal(body, &g); err != nil {
			fmt.Println(err.Error())
		} else {
			fmt.Println("JSON Response...")	
			fmt.Println("ID:      ", g.Id )
			fmt.Println("COUNT:   ", g.CountGumballs )
			fmt.Println("MODEL:   ", g.ModelNumber )	
			fmt.Println("SERIAL:  ", g.SerialNumber )	
		}
	}

	fmt.Println("\n===== Update Gumball Machine Inventory =====")
	reqbody := "{ \"CountGumballs\" : 1000 }"  
	client := &http.Client{}
	req, _ := http.NewRequest(http.MethodPut,  "http://localhost:3000/gumball", strings.NewReader(reqbody)  )
	req.Header.Add( "Content-Type", "application/json")
	resp, err = client.Do(req)
	defer resp.Body.Close()
	if err != nil {
		fmt.Println(err.Error())
	} else {
		body, _ := ioutil.ReadAll(resp.Body)
		//fmt.Println(string(body))
		fmt.Println( "Unmarshaling JSON Response...")
		var g gumballMachine
		if err := json.Unmarshal(body, &g); err != nil {
			fmt.Println(err.Error())
		} else {
			fmt.Println("JSON Response...")	
			fmt.Println("ID:      ", g.Id )
			fmt.Println("COUNT:   ", g.CountGumballs )
			fmt.Println("MODEL:   ", g.ModelNumber )	
			fmt.Println("SERIAL:  ", g.SerialNumber )	
		}
	}

	fmt.Println("\n===== Place An Order =====")
	//client = &http.Client{}
	req, _ = http.NewRequest(http.MethodPost,  "http://localhost:3000/order", nil  )
	req.Header.Add( "Content-Type", "application/json")
	resp, err = client.Do(req)
	defer resp.Body.Close()
	if err != nil {
		fmt.Println(err.Error())
	} else {
		body, _ := ioutil.ReadAll(resp.Body)
		//fmt.Println(string(body))
		fmt.Println( "Unmarshaling JSON Response...")
		var o order
		if err := json.Unmarshal(body, &o); err != nil {
			fmt.Println(err.Error())
		} else {
			fmt.Println("JSON Response...")	
			fmt.Println("ID:      ", o.Id )
			fmt.Println("STATUS:  ", o.OrderStatus )
		}
	}


}


/*

REF:  https://gist.github.com/maniankara/a10d19960293b34b608ac7ef068a3d63

client := &http.Client{}
	req, err := http.NewRequest(http.MethodPut, url, data)
	if err != nil {
		// handle error
		log.Fatal(err)
	}
	_, err = client.Do(req)
	if err != nil {
		// handle error
		log.Fatal(err)
}

*/
