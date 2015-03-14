// 2.22 - snippet of code that measures the time passed 
//        using console.time and console.timeEnd functions.

console.time('timer');
setTimeout(function(){
   console.timeEnd('timer');
},1000)