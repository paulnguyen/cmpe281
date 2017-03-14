
var async = require("async") ;

async.series({
    one: function(c){
        console.log ( "1" ) ; 
        c(null, "hello") ; // call two
    },
    two: function(c){
        console.log ( "2" ) ; 
        try {
            throw new Error("DANGER ZONE!");
        	c(null, "there") ; // call three
        }
        catch (err) {
            c(err, null) ; // call three
        }
    },
    three: function(c){
        console.log ( "3" ) ; 
        c(null, "class!") ;
    }
},
function(err, results) {
    console.log(err);
    console.log( results ) ;
}
);
