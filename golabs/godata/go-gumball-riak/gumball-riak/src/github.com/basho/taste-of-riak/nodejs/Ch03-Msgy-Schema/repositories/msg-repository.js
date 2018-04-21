'use strict';

var Repository = require('./repository');
var Msg = require('../models/msg');
var inherits = require('util').inherits;

function MsgRepository(client) {
    Repository.call(this, client);
    this.bucketName = 'Msgs';
}

inherits(MsgRepository, Repository);

MsgRepository.prototype.buildModel = function (rslt) {
    if (rslt.values) {
        var rv = null,
            riakObj = rslt.values.shift();
        if (riakObj && riakObj.value) {
            var msgJsonObj = riakObj.value;
            rv = new Msg(msgJsonObj.data.sender,
                msgJsonObj.data.recipient,
                msgJsonObj.data.text,
                new Date(msgJsonObj.data.created));
        }
        return rv;
    }
};

module.exports = MsgRepository;

