
/* npm install node-rest-client */

var Client = require('node-rest-client').Client;
var http = require('http') ;
var count = "";
var endpoint = "http://ec2-52-24-214-120.us-west-2.compute.amazonaws.com/GrailsGumballMachineVer2-2.0/gumball" ;


function request_handler (req, res) {
    var client = new Client();
            client.get( endpoint, function(data, response_raw){
                console.log(data[0].id) ;
                console.log(data[0].countGumballs) ;
                console.log(data[0].modelNumber) ;
                console.log(data[0].serialNumber) ;
                count = data[0].countGumballs
                console.log( "count = " + count ) ;
                res.end( "count = " + count + "\n");
            });
}

var s = http.createServer(request_handler) ;
s.listen(8080) ;

// Test with:  curl -X GET -i localhost:8080


