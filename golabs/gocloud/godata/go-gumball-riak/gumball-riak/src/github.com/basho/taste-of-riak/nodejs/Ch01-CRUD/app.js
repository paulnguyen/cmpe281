'use strict';

var async = require('async');
var assert = require('assert');
var logger = require('winston');
var Riak = require('basho-riak-client');

// Un-comment to enable debug logging
// logger.remove(logger.transports.Console);
// logger.add(logger.transports.Console, {
//     level : 'debug',
//     colorize: true,
//     timestamp: true
// });

var node = new Riak.Node({remoteAddress: 'riak-test', remotePort: 10017});
var cluster = new Riak.Cluster({nodes: [node]});
var client = new Riak.Client(cluster);

var val1 = 1;
var val2 = 'two';
var val3 = { 'myValue': 3 };

var bucket = 'test';

function checkErr(err) {
    if (err) {
        logger.error(err);
        process.exit(1);
    }
}

store_objects();

function store_objects() {
    logger.info('Creating Objects In Riak...');
    async.parallel([
        function (async_cb) {
            client.storeValue({ bucket: bucket, key: 'one', value: val1 }, async_cb);
        },
        function (async_cb) {
            client.storeValue({ bucket: bucket, key: 'two', value: val2 }, async_cb);
        },
        function (async_cb) {
            client.storeValue({ bucket: bucket, key: 'three', value: val3 }, async_cb);
        }
    ], function(err, rslts) {
        checkErr(err);
        fetch_objects();
    });
}

function fetch_objects() {
    logger.info("Reading Objects From Riak...");
    async.parallel({
        one: function (async_cb) {
                client.fetchValue({ bucket: bucket, key: 'one' }, function(err, rslt) {
                    async_cb(err, rslt);
                })
        },
        two: function (async_cb) {
                client.fetchValue({ bucket: bucket, key: 'two' }, function(err, rslt) {
                    async_cb(err, rslt);
                })
        },
        three: function (async_cb) {
                  client.fetchValue({ bucket: bucket, key: 'three' }, function(err, rslt) {
                      async_cb(err, rslt);
                  })
        }
    }, function(err, rslts) {
        checkErr(err);

        var one_obj = rslts.one.values.shift();
        var one_value = Number(one_obj.value.toString('utf8'));
        assert(val1 === one_value);

        var two_obj = rslts.two.values.shift();
        var two_value = two_obj.value.toString('utf8');
        assert(val2 === two_value);

        var three_obj = rslts.three.values.shift();
        var three_value = JSON.parse(three_obj.value.toString('utf8'));
        assert.deepEqual(three_value, val3);

        update_objects(three_obj);
    });
}

function update_objects(riakObj) {
    logger.info("Updating Object Three In Riak...");
    
    var new_val3 = { 'myValue': 42 };
    riakObj.setValue(new_val3);

    client.storeValue({
        value: riakObj,
        convertToJs: true,
        returnBody: true
    }, function (err, rslt) {
        checkErr(err);

        var updated_obj = rslt.values.shift();
        logger.debug("updated object key: " + updated_obj.key);
        logger.debug("updated object value: " + JSON.stringify(updated_obj.value));

        delete_objects();
    });
}

function delete_objects() {
    // Deleting Objects From Riak
    logger.info("Deleting Objects From Riak...");

    async.parallel([
        function (async_cb) {
            client.deleteValue({ bucket: bucket, key: 'one' }, async_cb);
        },
        function (async_cb) {
            client.deleteValue({ bucket: bucket, key: 'two' }, async_cb);
        },
        function (async_cb) {
            client.deleteValue({ bucket: bucket, key: 'three' }, async_cb);
        }
    ], function (err, rslts) {
        checkErr(err);

        complex_objects();
    });
}

function complex_objects() {
    // Working With Complex Objects
    logger.info("Working With Complex Objects...");

    var book = {
        'ISBN': '1111979723',
        'Title': 'Moby Dick',
        'Author': 'Herman Melville',
        'Body': 'Call me Ishmael. Some years ago...',
        'CopiesOwned': 3
    };

    client.storeValue({ bucket: 'book', key: book.ISBN, value: book }, function (err, rslt) {
        checkErr(err);

        client.fetchValue({ bucket: 'book', key: book.ISBN }, function (err, rslt) {
            checkErr(err);

            var object = rslt.values.shift();
            logger.info('Serialized Object: ' + object.value.toString('utf8'));

            client.shutdown(function (state) {
                if (state === Riak.Cluster.State.SHUTDOWN) {
                    logger.info("cluster stopped");
                    process.exit();
                }
            });
        });
    });
}

