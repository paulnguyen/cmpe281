'use strict';

var async = require('async');
var logger = require('winston');
var Riak = require('basho-riak-client');

// Uncomment to debug log
// logger.remove(logger.transports.Console);
// logger.add(logger.transports.Console, {
//     level : 'debug',
//     colorize: true,
//     timestamp: true
// });

function checkErr(err) {
    if (err) {
        logger.error(err);
        process.exit(1);
    }
}

logger.info("Starting Client");
var client = new Riak.Client([
    'riak-test:10017',
    'riak-test:10027',
    'riak-test:10037',
    'riak-test:10047'
]);

// Models
var User = require('./models/user');
var Msg = require('./models/msg');
var Timeline = require('./models/timeline');

// Repositories
var UserRepository = require('./repositories/user-repository');
var MsgRepository = require('./repositories/msg-repository');
var TimelineRepository = require('./repositories/timeline-repository');

// Managers
var TimelineManager = require('./timeline-manager');

// instances
var userRepo = new UserRepository(client);
var msgRepo = new MsgRepository(client);
var timelineRepo = new TimelineRepository(client);
var timelineMgr = new TimelineManager(timelineRepo, msgRepo);

// Create and save users
var marleen = new User('marleenmgr', 'Marleen Manager', 'marleen.manager@basho.com');
var joe = new User('joeuser', 'Joe User', 'joe.user@basho.com');

async.parallel([
        function (async_cb) {
            userRepo.save(marleen, async_cb);
        },
        function (async_cb) {
            userRepo.save(joe, async_cb);
        }
    ],
    function (err, rslts) {
        checkErr(err);
        rslts.forEach(function (rslt) {
            logger.info("rslt.fullName: %s", rslt.fullName);
        });
        create_message();
    }
);

function create_message() {
    var msg = new Msg(marleen.userName, joe.userName, 'Welcome to the company!');
    timelineMgr.postMsg(msg, function (err, rslts) {
        checkErr(err);
        get_inbox();
    });
}

function get_inbox() {
    // Get Joe's inbox for today, get first message
    timelineMgr.getTimeline(
        joe.userName, Timeline.TimelineType.Inbox, new Date(), function (err, rslt) {
            checkErr(err);
            msgRepo.get(rslt.msgKeys.shift(), false, function (err, rslt) {
                checkErr(err);
                logger.info("From: " + rslt.sender);
                logger.info("Msg : " + rslt.text);
                client_shutdown();
            });
        });
}

function client_shutdown() {
    logger.info("shutting down");
    client.shutdown(function (state) {
        if (state === Riak.Cluster.State.SHUTDOWN) {
            logger.info("cluster stopped");
            process.exit();
        }
    });
}
