/*

Test:

curl -X GET -i http://localhost:9090/api/unixtime?iso=2013-08-10T12:10:15.474Z 
curl -X GET -i http://localhost:9090/api/parsetime?iso=2013-08-10T12:10:15.474Z 

Run:
 
node 22-json-api.js 9090

*/


var http = require('http');
var url = require('url');

http.createServer(function (req, res) {
  var urlObj = url.parse(req.url, true),
      pathname = urlObj.pathname,
      strtime = urlObj.query.iso,
      result;

  if (pathname === '/api/unixtime') {
     result = getUnixTimestamp(strtime);
  }
  else if (pathname === '/api/parsetime') {
    result = getTimeObj(strtime);
  }

  if (result) {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify(result));
  }
  else {
    res.writeHead(404);
    res.end();
  }
  
}).listen(process.argv[2]);


function getUnixTimestamp(strtime) {
  return {
    unixtime: getTimestamp(strtime)
  };  
}

function getTimestamp(strtime) {
  return Date.parse(strtime);
}

function getTimeObj(strtime) {
  var date = new Date(getTimestamp(strtime));

  return {
    hour: date.getHours(),
    minute: date.getMinutes(),
    second: date.getSeconds()
  };
}

 