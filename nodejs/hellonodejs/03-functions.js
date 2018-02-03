

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

// 2.14 Anonymous Functions + New Var Scope (alternate version)
var foo = 123;
if (true) {
    var f = function () { // create a new scope
        var foo = 456;
    };
    f() ;
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


// Function Scope & Closure

var outer = function() {
    var a = "test"
    return function bar() {
        console.log(a); 
    }
}

r = outer() ;
r() ;