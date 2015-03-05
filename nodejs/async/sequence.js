

var f3 = function (err, res) { console.log("3"); }

var f2 = function (err, res) { 
    console.log("2");
    setTimeout( f3, 1); 
}

var f1 = function (err, res) { 
    console.log("1"); 
    setTimeout( f2, 1) ;
}

setTimeout( f1, 1 );
