/*
	Gumball API in Go (Version 3)
	Process Order with Go Channels
	Removed Use of Mutex
*/

package main

import (
	"os"
)

func main() {

	port := os.Getenv("PORT")
	if len(port) == 0 {
		port = "3000"
	}

	server := NewServer()
	server.Run(":" + port)
}


/**

	Kong API Setup:
	==============


	Create API
	----------

	curl -X POST \
	  http://dockerhost:8001/apis \
	  -H 'content-type: application/json' \
	  -d '{
	    "name": "goapi",
	    "request_path": "/goapi",
	    "strip_request_path": "true",
	    "preserve_host": "true",
	    "upstream_url": "http://gumball:3000/"
	}'

	Add API Key Plugin
	------------------

	curl -X POST \
	  http://dockerhost:8001/apis/goapi/plugins \
	  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW'


	Add API Key
	-----------

	curl -X POST \
	  http://dockerhost:8001/consumers/apiclient/key-auth \
	  -H 'content-type: text/plain'
  


**/