
// 2.23 - Now we have an event that can be raised from the Node.js 
//        event loop (setTimeout) and a function that can keep 
//        the JavaScript thread busy (fibonacci).

console.time('timeit');
function fibonacci(n) {
    if (n < 2)
        return 1;
    else
        return fibonacci(n - 2) + fibonacci(n - 1);
}
fibonacci(44);             // modify this number based on your system performance
console.timeEnd('timeit'); // On my system it takes about 9000ms (i.e. 9 seconds)