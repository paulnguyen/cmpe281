'use strict';
var Model = require('./model');
var inherits = require('util').inherits;

function Msg(sender, recipient, text, created) {
    Model.call(this);

    var created_date = new Date();
    if (created && created instanceof Date) {
        created_date = created;
    }

    this.data = Object.freeze({
        sender: sender,
        recipient: recipient,
        text: text,
        created: created_date
    });

    Model.defineProperties(this, this.data);
}

inherits(Msg, Model);

Msg.prototype.getId = function () {
    return this.sender + "_" + this.created.toISOString();
};

module.exports = Msg;

