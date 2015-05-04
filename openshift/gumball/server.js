#!/bin/env node
//  OpenShift sample Node application
var express = require('express');
var fs      = require('fs');
var Client  = require('node-rest-client').Client;


/**
 * Gumball Handlers
 */

var page = function( req, res, state ) {
    var body = fs.readFileSync('./gumball.html');
    res.setHeader('Content-Type', 'text/html');
    res.writeHead(200);

    var client = new Client();
            var count = "";
            client.get("http://pnguyen-gumball-v2.elasticbeanstalk.com/gumballs/1", 
                function(data, response_raw){
                    console.log(data);
                    count = data.countGumballs
                    console.log( "count = " + count ) ;
                    var msg =   "\n\nMighty Gumball, Inc.\n\nNodeJS-Enabled Standing Gumball\nModel# " + 
                                data.modelNumber + "\n" +
                                "Serial# " + data.serialNumber + "\n" ;
                    var html_body = "" + body ;
                    var html_body = html_body.replace("{message}", msg );
                    var html_body = html_body.replace(/id="state".*value=".*"/, "id=\"state\" value=\""+state+"\"") ;
                    res.end( html_body );
            });
}

var handle_get = function (req, res) {
    console.log( "Get: ..." ) ;
    page( req, res, "no-coin" ) ;
}

var order = function(req, res) {
    var client = new Client();
            var count = 0;
            client.get("http://pnguyen-gumball-v2.elasticbeanstalk.com/gumballs/1", 
                function(data, response_raw) {
                    count = data.countGumballs ;
                    console.log( "count before = " + count ) ;
                    if ( count > 0 )
                        count-- ;
                    console.log( "count after = " + count ) ;
                    var args = {
                        data: {  "countGumballs": count },
                        headers:{"Content-Type": "application/json"} 
                    };
                    client.put( "http://pnguyen-gumball-v2.elasticbeanstalk.com/gumballs/1", args,
                        function(data, response_raw) {
                            console.log(data);
                            page( req, res, "no-coin" ) ;
                        } 
                    );
            });
}

var handle_post = function (req, res) {
    console.log( "Post: " + "Action: " +  req.body.event + " State: " + req.body.state + "\n" ) ;
    var state = "" + req.body.state ;
    var action = "" + req.body.event ;
    if ( action == "Insert Quarter" ) {
        if ( state == "no-coin" )
            page( req, res, "has-coin" ) ;
        else
            page( req, res, state ) ;
            
    }
    else if ( action == "Turn Crank" ) {
        if ( state == "has-coin" ) {
            order(req, res) ;
        }
        else
             page( req, res, state ) ;
    }    
}


/**
 *  Define the sample application.
 */
var SampleApp = function() {

    //  Scope.
    var self = this;


    /*  ================================================================  */
    /*  Helper functions.                                                 */
    /*  ================================================================  */

    /**
     *  Set up server IP address and port # using env variables/defaults.
     */
    self.setupVariables = function() {
        //  Set the environment variables we need.
        self.ipaddress = process.env.OPENSHIFT_NODEJS_IP;
        self.port      = process.env.OPENSHIFT_NODEJS_PORT || 8080;

        if (typeof self.ipaddress === "undefined") {
            //  Log errors on OpenShift but continue w/ 127.0.0.1 - this
            //  allows us to run/test the app locally.
            console.warn('No OPENSHIFT_NODEJS_IP var, using 127.0.0.1');
            self.ipaddress = "127.0.0.1";
        };
    };


    /**
     *  Populate the cache.
     */
    self.populateCache = function() {
        if (typeof self.zcache === "undefined") {
            self.zcache = { 'gumball.html': '' };
        }

        //  Local cache for static content.
        self.zcache['gumball.html'] = fs.readFileSync('./gumball.html');
    };


    /**
     *  Retrieve entry (content) from cache.
     *  @param {string} key  Key identifying content to retrieve from cache.
     */
    self.cache_get = function(key) { return self.zcache[key]; };


    /**
     *  terminator === the termination handler
     *  Terminate server on receipt of the specified signal.
     *  @param {string} sig  Signal to terminate on.
     */
    self.terminator = function(sig){
        if (typeof sig === "string") {
           console.log('%s: Received %s - terminating sample app ...',
                       Date(Date.now()), sig);
           process.exit(1);
        }
        console.log('%s: Node server stopped.', Date(Date.now()) );
    };


    /**
     *  Setup termination handlers (for exit and a list of signals).
     */
    self.setupTerminationHandlers = function(){
        //  Process on exit and signals.
        process.on('exit', function() { self.terminator(); });

        // Removed 'SIGPIPE' from the list - bugz 852598.
        ['SIGHUP', 'SIGINT', 'SIGQUIT', 'SIGILL', 'SIGTRAP', 'SIGABRT',
         'SIGBUS', 'SIGFPE', 'SIGUSR1', 'SIGSEGV', 'SIGUSR2', 'SIGTERM'
        ].forEach(function(element, index, array) {
            process.on(element, function() { self.terminator(element); });
        });
    };


    /*  ================================================================  */
    /*  App server functions (main app logic here).                       */
    /*  ================================================================  */

    /**
     *  Create the routing table entries + handlers for the application.
     */
    self.createRoutes = function() {
        self.routes = { };

        self.routes['/asciimo'] = function(req, res) {
            var link = "http://i.imgur.com/kmbjB.png";
            res.send("<html><body><img src='" + link + "'></body></html>");
        };

        self.routes['/hello'] = function(req, res) {
            var link = "http://i.imgur.com/kmbjB.png";
            res.send("<html><body>Hello Node.js on OpenShift</body></html>");
        };
        
        self.routes['/testhtml'] = function(req, res) {
            res.setHeader('Content-Type', 'text/html');
            res.send(self.cache_get('gumball.html') );
        };

        self.routes['/testrest'] = function(req, res) {
            var client = new Client();
            var count = "";
            client.get("http://pnguyen-gumball-v2.elasticbeanstalk.com/gumballs/1", 
                function(data, response_raw){
                    console.log(data);
                    count = data.countGumballs
                    console.log( "count = " + count ) ;
                    res.send( "count = " + count  );
            });
        };

        
    };


    /**
     *  Initialize the server (express) and create the routes and register
     *  the handlers.
     */
    self.initializeServer = function() {
        self.createRoutes();
        self.app = express.createServer();

        // Add Path to Images Folder
        self.app.use("/images", express.static(__dirname + '/images'));
        
        // Setup Body Parser
        self.app.use(express.bodyParser());

        //  Add handlers for the app (from the routes).
        for (var r in self.routes) {
            self.app.get(r, self.routes[r]);
        }
        
        // Add Gumball Handlers
        self.app.post("/", handle_post );
        self.app.get( "/", handle_get ) ;
    };


    /**
     *  Initializes the sample application.
     */
    self.initialize = function() {
        self.setupVariables();
        self.populateCache();
        self.setupTerminationHandlers();

        // Create the express server and routes.
        self.initializeServer();
    };


    /**
     *  Start the server (starts up the sample application).
     */
    self.start = function() {
        //  Start the app on the specific interface (and port).
        self.app.listen(self.port, self.ipaddress, function() {
            console.log('%s: Node server started on %s:%d ...',
                        Date(Date.now() ), self.ipaddress, self.port);
        });
    };

};   /*  Sample Application.  */



/**
 *  main():  Main code.
 */
var zapp = new SampleApp();
zapp.initialize();
zapp.start();

