
// web_form.js
// Example from: http://stackoverflow.com/questions/17762188/simple-form-node-js-application
//

var http = require('http'); 
var util = require('util');

// Create a function to handle every HTTP request

function handler(req, res){
  if(req.method == "GET"){ 
    console.log('get');
    res.setHeader('Content-Type', 'text/html');
    res.writeHead(200);
    res.end("<html><body><form action='/' method='post'><input type='text' name='hello'><input type='submit'></form></body></html>");
  } else if(req.method == 'POST'){
    console.log('post');
    // Here you could use a library to extract the form content
    // The Express.js web framework helpfully does just that
    // For simplicity's sake we will always respond with 'hello world' here
    // var hello = req.body.hello;
    var hello = 'world';
    res.setHeader('Content-Type', 'text/html');
    res.writeHead(200);
    res.end("<html><body><h1>Hello "+hello+"!</h1></body></html>");
  } else {
    res.writeHead(200);
    res.end();
  };
};


// Create a server that invokes the `handler` function upon receiving a request
// And have that server start listening for HTTP requests
// The callback function is executed at some time in the future, when the server
// is done with its long-running task (setting up the network and port etc)
http.createServer(handler).listen(8080, function(err){
  if(err){
    console.log('Error starting http server');
  } else {
    console.log('Server listening on port 8080');
  };
});