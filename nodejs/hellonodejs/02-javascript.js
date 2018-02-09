

// 2.1  js variables
var foo = 123;
console.log(foo); // 123

// 2.2  js numbers are all floating point
var foo = 3;
var bar = 5;
console.log(foo + 1);   // 4
console.log(foo / bar); // 0.6
console.log(foo * bar); // 15
console.log(foo - bar); // -2;
console.log(foo % 2);   // remainder: 1

// 2.3  Boolean operations (&&, ||, !) work as expected:
var foo = true;
console.log(foo); // true
console.log(true && true);   // true
console.log(true && false);  // false
console.log(true || false);  // true
console.log(false || false); // false
console.log(!true);          // false
console.log(!false);         // true

// 2.4  Arrays.  Zero based index as you would expect.
var foo = [ ];
foo.push(1);         // add at the end
console.log(foo);    // prints [1]
foo.unshift(2);      // add to the top
console.log(foo);    // prints [2,1]
// Arrays are zero index based:
console.log(foo[0]); // prints 2


// 2.5  Object Literals (Dynamic Properties)
var foo = { };
console.log(foo); // {}
foo.bar = 123;    // extend foo
console.log(foo); // { bar: 123 }

// 2.6  Object Literals (Predefined Properties)
var foo = {
    bar: 123
};
console.log(foo); // { bar: 123 }

// 2.7  Object Literals (Nested)
var foo = {
    bar: 123,
    bas: {
        bas1: 'some string',
        bas2: 345
    }
};
console.log(foo);

// 2.8  Object Literals with Arrays
var foo = {
    bar: 123,
    bas: [1, 2, 3]
};
console.log(foo);

// 2.9  Object Literals with Arrays of Object Literals
var foo = {
    bar: 123,
    bas: [{
        qux: 1
    },
    {
        qux: 2
    },
    {
        qux: 3
    }]
};
console.log(foo.bar);        // 123
console.log(foo.bas[0].qux); // 1
console.log(foo.bas[2].qux); // 2



// JS Arrays
var array1 = [1, 2, 3, 4]
var array2 = ["one", "two", "three", "four"]
var array3 = [ array1, array2 ]
console.log ( array1 ) ;
console.log ( array2 ) ;
console.log ( array3 ) ;

// JS Objects 
var person = new Object();
person.firstname = "John";
person.lastname = "Doe";  
person.getFullName = function(){
    console.log (person.firstname+' '+person.lastname);
};
person.getFullName();

// Hello World JSON
var json = {"Hello":"Hello World"} ;
console.log(json.Hello) ;
console.log(json["Hello"]) ;

// Hello World JS Object - Literal Notation
var jsobj = { Hello : "Hello World" } ;
console.log( jsobj.Hello ) ;
console.log( jsobj["Hello"] ) ;

// Hello World JS Object - Constructor Notation
var jsobj2 = new Object() ;
jsobj2.Hello = "Hello World" ;
console.log( jsobj2.Hello ) ;

// Hello World JS Object - Constructor Function
function HelloClass(msg) {
  this.Hello = msg;
}
var jsobj3 = new HelloClass("Hello World") ;
console.log( jsobj3.Hello ) ;

// JSON Object
var json_person = {
    "id": 101,
    "name": "John Doe",
    "isStudent": true,
    "scores": [
        10,
        20,
        30,
        40
    ],
    "courses": {
        "major": "Finance",
        "minor": "Marketing"
    }
} ;

// JS Object
var js_person = {
    id: 101,
    name: "John Doe",
    isStudent: true,
    scores: [
        10,
        20,
        30,
        40
    ],
    courses: {
        "major": "Finance",
        "minor": "Marketing"
    },
    printName: function() {
        console.log( this.name ) ;
    }
}

//  JSON Supports Data Types:  strings, numbers, Booleans, arrays, objects, and null
var stringVal = "Hello World" ;
var numberVal = 1234 ;
var booleanVal = true ;
var arrayVal = [ 1, 2, 3 ] ;
var objectVal = { "id" : "123", "name" : "Jonh Smith" }
var nullVal1 = "" ;
var nullVal2 = null ;
console.log( "String = " + stringVal + "  // type = " + typeof(stringVal) ) ;
console.log( "Number = " + numberVal + " // type = " + typeof(numberVal) ) ;
console.log( "Boolean = " + booleanVal + " // type = " + typeof(booleanVal) ) ;
console.log( "Array = " + arrayVal + " // type = " + typeof(arrayVal) ) ;
console.log( "Object = " + objectVal + " // type = " + typeof(objectVal) ) ;
console.log( "Null Val1 = " + nullVal1 + " // type = " + typeof(nullVal1) ) ;
console.log( "Null Val2 = " + nullVal2 + " // type = " + typeof(nullVal2) ) ;

// BIOS Example
var john =
{
    "name" : {
        "first" : "John",
        "last" : "Backus"
    },
    "birth" : "1924-12-03T05:00:00Z",
    "death" : "2007-03-17T04:00:00Z",
    "contribs" : [
        "Fortran",
        "ALGOL",
        "Backus-Naur Form",
        "FP"
    ],
    "awards" : [
        {
            "award" : "W.W. McDowell Award",
            "year" : 1967,
            "by" : "IEEE Computer Society"
        },
        {
            "award" : "National Medal of Science",
            "year" : 1975,
            "by" : "National Science Foundation"
        },
        {
            "award" : "Turing Award",
            "year" : 1977,
            "by" : "ACM"
        },
        {
            "award" : "Draper Prize",
            "year" : 1993,
            "by" : "National Academy of Engineering"
        }
    ]    
} ;