

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