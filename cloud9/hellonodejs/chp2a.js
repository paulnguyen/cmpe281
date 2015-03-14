
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
var foo = [];
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




// 2.10 JavaScript Functions
function functionName() {
    // function body
    // optional return;
}

// 2.11 Return from Functions
function foo() { return 123; }
console.log(foo()); // 123
function bar() { }
console.log(bar()); // undefined

// 2.12 Define and Invoke Functions in "one shot"
(function foo() {
    console.log('foo was executed!');
})();

// 2.13 Functions create a "new variable scope".  If, Else, While don't
var foo = 123;
if (true) {
    var foo = 456;
}
console.log(foo); // 456;

// 2.14 Anonymous Functions + New Var Scope
var foo = 123;
if (true) {
    (function () { // create a new scope
        var foo = 456;
    })();
}
console.log(foo); // 123;

// 2.15 Anonymous Functions don't need Names
var foo1 = function namedFunction() { // no use of name, just wasted characters
    console.log('foo1');
}
foo1(); // foo1

var foo2 = function () {              // no function name given i.e. anonymous function
    console.log('foo2');
}
foo2(); // foo2

// 2.16 1st Class Functions & Higher-Order Functions (passed in-line)
setTimeout(function () {
    console.log('2000 milliseconds have passed since this demo started');
}, 2000);

// 2.17 Higer-Order Functions (Define Function first)
function foo() {
    console.log('2000 milliseconds have passed since this demo started');
}
setTimeout(foo, 2000);

// 2.18 Closure (inner functions encloses outer vars/scope)
function outerFunction(arg) {
    var variableInOuterFunction = arg;

    function bar() {
        console.log(variableInOuterFunction); // Access a variable from the outer scope
    }

    // Call the local function to demonstrate that it has access to arg
    bar();
}
outerFunction('hello closure!'); // logs hello closure!

// 2.19 Closures work even when outer function out-of-scope
function outerFunction(arg) {
    var variableInOuterFunction = arg;
    return function () {
        console.log(variableInOuterFunction);
    }
}

var innerFunction = outerFunction('hello closure!');

// Note the outerFunction has returned
innerFunction(); // logs hello closure!


