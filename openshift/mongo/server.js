#!/bin/env node

//  OpenShift sample Node application
var express = require('express');
var fs      = require('fs');
var mongoose = require('mongoose');
var chance = require('chance').Chance();

var db_host = process.env.OPENSHIFT_MONGODB_DB_HOST ;
var db_port = process.env.OPENSHIFT_MONGODB_DB_PORT ;
var db_user = process.env.OPENSHIFT_MONGODB_DB_USERNAME ;
var db_pwd  = process.env.OPENSHIFT_MONGODB_DB_PASSWORD ;
var db_name = "mongo" ;

var node_ip = process.env.OPENSHIFT_NODEJS_IP
var node_port = process.env.OPENSHIFT_NODEJS_PORT

var db_connection_string = process.env.OPENSHIFT_MONGODB_DB_USERNAME + ":" +
    process.env.OPENSHIFT_MONGODB_DB_PASSWORD + "@" +
    process.env.OPENSHIFT_MONGODB_DB_HOST + ':' +
    process.env.OPENSHIFT_MONGODB_DB_PORT + '/' +
    process.env.OPENSHIFT_APP_NAME;
    

/* DB Connection for Mongoose */
var dburi = "mongodb://"+db_host+":"+db_port+"/"+db_name+""
mongoose.connect( dburi );

/* Mongoose Data Model */
var Schema = mongoose.Schema;
var OrderSchema = new Schema({
  OrdNum:  String,   /* 120981, Etc... */
  OrdStatus: String  /* Backorder, Submitted, Shipped, Etc... */
});   

/* Mongoose Schema */
var GumballOrder = mongoose.model('GumballOrder', OrderSchema);

/* DB Connection for Direct API */
var DB = require('mongodb').Db,
    DB_Connection = require('mongodb').Connection,
    DB_Server = require('mongodb').Server,
    async = require('async') ;

var db = new DB(db_name,
                new DB_Server( db_host, db_port,
                            { auto_reconnect: true,
                             poolSize: 20}),
                            { w: 1 } );    
    
/* DB Init for Direct API */
var db_init = function (callback) {
    async.waterfall([
        // 1. open database 
        function (cb) {
            console.log("INIT: STEP 1. Open MongoDB...");
            db.open(cb);
        },
        // 2. authenticate
        function (result, cb) {
            console.log("INIT: STEP 2. Authenticate...");
            db.authenticate(db_user, db_pwd, function(err, res) {
                        if(!err) {
                            console.log("Authenticated");
                            cb(null, callback) ;
                        } else {
                            console.log("Error in authentication.");
                            console.log(err);
                            process.exit(-1);
                        }
                    });
        },
        // 3. fetch collections
        function (result, cb) {
            console.log("INIT: STEP 3. Fetch Collections...");
            db.collections(cb);
        },

    ], callback);
};



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
            self.zcache = { 'index.html': '' };
        }

        //  Local cache for static content.
        self.zcache['index.html'] = fs.readFileSync('./index.html');
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
            console.log("Node IP: " + node_ip ) ;
            console.log("Node Port: " + node_port ) ;
            console.log("MongoDB: " + db_connection_string);
            var link = "http://i.imgur.com/kmbjB.png";
            res.send("<html><body>Hello Node.js on OpenShift</body></html>");
        };

        self.routes['/mongo1'] = function(req, res) {
            var num = chance.natural().toString();
            var order = new GumballOrder( { OrdNum: num, OrdStatus: 'Submitted' } );
            order.save(function (err) {
                if (err)
                console.log('Save Error:' + err);
            }) ;
            console.log( "POST: \n" + order ) ;
            res.writeHead(200, {"Content-Type": "application/json"});
            res.end(JSON.stringify(order) + "\n");
        };

        self.routes['/mongo2'] = function(req, res) {
            var num = chance.natural().toString();
            db.collection('gumballorders', function(err, collection) {
                if (err) {
                     res.end(err);
                }
                else {
                    collection.insert( { OrdNum: num, OrdStatus: "Backorder" }, function(err, collection) {
                        if (err)
                            res.end(err) ;
                        else
                            res.end("Document Inserted: New Order Created!") ;
                    }) ;
                }
            }) ;
        } ;

        self.routes['/'] = function(req, res) {
            res.setHeader('Content-Type', 'text/html');
            res.send(self.cache_get('index.html') );
        };
    };


    /**
     *  Initialize the server (express) and create the routes and register
     *  the handlers.
     */
    self.initializeServer = function() {
        self.createRoutes();
        self.app = express.createServer();

        //  Add handlers for the app (from the routes).
        for (var r in self.routes) {
            self.app.get(r, self.routes[r]);
        }
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

    self.dbInitialize = function () {
        db_init(function (err, results) {
            if (err) {
                console.error("DB FATAL ERROR INIT:");
                console.error(err);
            } else {
                console.log( "DB INIT SUCCESSFUL!" ) ;
            }
        });
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
zapp.dbInitialize();
zapp.start();

