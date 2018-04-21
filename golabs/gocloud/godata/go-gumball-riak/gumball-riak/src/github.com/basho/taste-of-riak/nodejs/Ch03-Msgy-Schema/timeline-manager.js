'use strict';

var Msg = require('./models/msg');
var Timeline = require('./models/timeline');

function TimelineManager(argTimelineRepository, argMsgRepository) {
    this.timelineRepository = argTimelineRepository;
    this.msgRepository = argMsgRepository;

    this.addToTimeline = function (msg, timelineType, msgKey, callback) {
        var self = this,
            timelineKey = this.generateKeyFromMsg(msg, timelineType);

        self.timelineRepository.get(timelineKey, true, function (err, timeline) {
            if (timeline) {
                timeline = addToExistingTimeline(timeline, msgKey);
            } else {
                timeline = createNewTimeline(timelineKey, msgKey);
            }

            self.timelineRepository.save(timeline, callback);
        });
    }

    this.generateKey = function (ownerUsername, timelineType, date) {
        if (ownerUsername && timelineType && date) {
            var dateString = date.toISOString().substring(0, 10);
            return ownerUsername + "_" + timelineType + "_" + dateString;
        } else {
            throw new Error(
                "ownerUsername: " + ownerUsername + ", " +
                "timelineType: " + timelineType + ", " +
                "date: " + date);
        }
    }

    this.generateKeyFromMsg = function (msg, timelineType) {
        var owner = getOwner(msg, timelineType);
        return this.generateKey(owner, timelineType, msg.created);
    }

    function addToExistingTimeline(timeline, msgKey) {
        timeline.addMsg(msgKey);
        return timeline;
    }

    function createNewTimeline(timelineKey, msgKey) {
        var newTimeline = new Timeline(timelineKey);
        newTimeline.addMsg(msgKey);
        return newTimeline;
    }

    function getOwner(msg, timelineType) {
        switch (timelineType)
        {
            case Timeline.TimelineType.Inbox:
                return msg.recipient;
            case Timeline.TimelineType.Sent:
                return msg.sender;
        }
        return msg.recipient;
    }
}

TimelineManager.prototype.postMsg = function (msg, callback) {
    var self = this;
    self.msgRepository.save(msg, function (err, savedMsg) {
        if (err) {
            callback(err, null);
        }
        // Post to recipient's Inbox timeline
        self.addToTimeline(msg, Timeline.TimelineType.Inbox, savedMsg.id, function (err, recipientRslt) {
            if (err) {
                callback(err, null);
            }
            // Post to sender's Sent timeline
            self.addToTimeline(msg, Timeline.TimelineType.Sent, savedMsg.id, function (err, senderRslt) {
                if (err) {
                    callback(err, null);
                } else {
                    callback(null, [ recipientRslt, senderRslt ]);
                }
            });
        });
    });
};

TimelineManager.prototype.getTimeline = function (ownerUsername, timelineType, date, callback) {
    var timelineKey = this.generateKey(ownerUsername, timelineType, date);
    this.timelineRepository.get(timelineKey, false, callback);
}

module.exports = TimelineManager;

