'use strict';

var logger = require('winston');
var Model = require('../models/model');

function Repository(client_arg) {
    this.client = client_arg;
}

Repository.prototype.get = function (key, notFoundOk, callback) {
    var self = this,
        bucket = this.getBucketName();
    self.client.fetchValue({
            bucket: bucket, key: key,
            notFoundOk: notFoundOk,
            convertToJs: true
        },
        function (err, rslt) {
            if (err) {
                callback(err, null);
            } else {
                callback(err, self.buildModel(rslt));
            }
        }
    );
};

Repository.prototype.save = function (model, callback) {
    if (!(model instanceof Model)) {
        throw new Error('argument must be a Model object');
    }

    var self = this,
        bucket = this.getBucketName();

    self.client.fetchValue({ bucket: bucket, key: model.id, notFoundOk: true }, function (err, rslt) {
        if (err) {
            callback(err, null);
        } else {
            logger.debug("rslt.values.length %d", rslt.values.length);
            var objToInsertOrUpdate;
            if (rslt.values.length > 1) {
                // Siblings present that need resolution
                // Here we'll just assume the first sibling is the "correct" one
                // with which to update with the new Model data
                // A conflict resolver can also be part of the options to fetchValue above
                var riakObj = rslt.values.shift();
                riakObj.setValue(model);
                objToInsertOrUpdate = riakObj;
            } else {
                objToInsertOrUpdate = model;
            }

            self.client.storeValue({
                    bucket: bucket, key: model.id,
                    returnBody: true, value: objToInsertOrUpdate,
                    convertToJs: true
                },
                function (err, rslt) {
                    if (err) {
                        callback(err, null);
                    } else {
                        callback(err, self.buildModel(rslt));
                    }
                }
            );
        }
    });
};

Repository.prototype.getBucketName = function () {
    if (this.hasOwnProperty('bucketName')) {
        return this.bucketName;
    } else {
        throw 'not implemented';
    }
};

Repository.prototype.buildModel = function (rslt) {
    throw 'not implemented';
};

module.exports = Repository;

