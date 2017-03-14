


// https://github.com/maxogden/art-of-node

var fs = require('fs') // require is a special function provided by node
var myNumber = undefined // we don't know what the number is yet since it is stored in a file

function addOne() {
  fs.readFile('05-seq-but-not.txt', function doneReading(err, fileContents) {
    myNumber = parseInt(fileContents)
    myNumber++
  })
}

addOne()

function f1() {
	console.log(myNumber) 
}

setTimeout( f1, 9000 )



