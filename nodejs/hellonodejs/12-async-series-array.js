
var async = require("async") ;

async.series(
    [
        function(c){
            console.log ( "1" ) ; 
            c(null, "hello") ; // call two
        },
        function(c){
            console.log ( "2" ) ; 
            c(null, "there") ; // call threee
        },
        function(c){
            console.log ( "3" ) ; 
            c(null, "class!") ;
        }
    ],
    function(err, results) {
        console.log( results ) ;
    }
);
