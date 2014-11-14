var Client = require('node-rest-client').Client;
var http = require('http') ;

function request_handler (req, res) {

    var body = "Hey, Thanks for calling!\n" ;
    var content_length = body.length ;

    res.writeHead( 200, {
        'Content-Type' : "text/plain",
        'Content-Length' : content_length
    }) ;

    var client = new Client();
            var count = "";
            client.get("http://pnguyen-grails-gumball-v2.cfapps.io/gumballs", function(data, response_raw){
                // parsed response body as js object
                console.log(data);
                // raw response
                //console.log(response_raw);
                // just the properties we need
                console.log(data[0].id) ;
                console.log(data[0].countGumballs) ;
                console.log(data[0].modelNumber) ;
                console.log(data[0].serialNumber) ;
                count = data[0].countGumballs
                console.log( "count = " + count ) ;
            });
    res.end(body);
}

var s = http.createServer(request_handler) ;
s.listen(8080) ;

// Test with:  curl -X GET -i localhost:8080