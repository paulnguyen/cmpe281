'use strict';
var Model = require('./model');
var inherits = require('util').inherits;

function Timeline(id) {
    Model.call(this);

    this.data = Object.freeze({
        id: id,
        msgKeys: []
    })

    Model.defineProperties(this, this.data);
}

inherits(Timeline, Model);

Timeline.prototype.addMsg = function (msgKey) {
    this.msgKeys.push(msgKey);
};

var TimelineType = Object.freeze({ Inbox : 'INBOX', Sent : 'SENT' });

module.exports = Timeline;
module.exports.TimelineType = TimelineType;

