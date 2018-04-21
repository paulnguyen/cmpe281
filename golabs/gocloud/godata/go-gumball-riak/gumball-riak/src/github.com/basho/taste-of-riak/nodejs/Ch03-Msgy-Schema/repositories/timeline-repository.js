'use strict';

var Repository = require('./repository');
var Timeline = require('../models/timeline');
var inherits = require('util').inherits;

function TimelineRepository(client) {
    Repository.call(this, client);
    this.bucketName = 'Timelines';
}

inherits(TimelineRepository, Repository);

TimelineRepository.prototype.buildModel = function (rslt) {
    if (rslt.values) {
        var rv = null,
            riakObj = rslt.values.shift();
        if (riakObj && riakObj.value) {
            var timelineJsonObj = riakObj.value;
            var model = new Timeline(timelineJsonObj.data.id);
            timelineJsonObj.data.msgKeys.forEach(function (msgKey) {
                model.addMsg(msgKey);
            });
            rv = model;
        }
        return rv;
    }
};

module.exports = TimelineRepository;

