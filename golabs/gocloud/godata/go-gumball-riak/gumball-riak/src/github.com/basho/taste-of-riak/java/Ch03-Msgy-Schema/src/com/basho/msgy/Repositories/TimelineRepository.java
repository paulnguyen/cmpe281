package com.basho.msgy.Repositories;

import com.basho.msgy.Models.Msg;
import com.basho.msgy.Models.Timeline;
import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimelineRepository {

    static final String BUCKET_NAME = "Timelines";
    protected IRiakClient client;
    protected MsgRepository msgRepo;

    public TimelineRepository(IRiakClient client) {
        this.client = client;
        this.msgRepo = new MsgRepository(this.client);
    }

    public void postMsg(Msg msg) throws RiakRetryFailedException {
        String msgKey = msgRepo.save(msg);

        // Post to recipient's Inbox timeline
        addToTimeline(msg, Timeline.TimelineType.Inbox, msgKey);

        // Post to sender's Sent timeline
        addToTimeline(msg, Timeline.TimelineType.Sent, msgKey);
    }


    private void addToTimeline(Msg msg, Timeline.TimelineType type, String msgKey) throws RiakRetryFailedException {
        String timelineKey = generateKeyFromMsg(msg, type);

        Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
        Timeline timeline = bucket.fetch(timelineKey, Timeline.class).execute();

        if(timeline != null) {
            timeline = addToExistingTimeline(timeline,msgKey);
        } else {
            timeline = createNewTimeline(msg, type, msgKey);
        }

        bucket.store(timelineKey, timeline).execute();
    }

    public Timeline createNewTimeline(Msg msg, Timeline.TimelineType type, String msgKey) {
        String owner = getOwner(msg, type);

        Timeline newTimeline = new Timeline();
        newTimeline.Owner = owner;
        newTimeline.Type = type.toString();
        newTimeline.Msgs.add(msgKey);

        return newTimeline;
    }

    public Timeline addToExistingTimeline(Timeline timeline, String msgKey) {
        timeline.Msgs.add(msgKey);
        return timeline;
    }

    public Timeline getTimeline(String ownerUsername, Timeline.TimelineType type, Date date) throws RiakRetryFailedException {
        String timelineKey = generateKey(ownerUsername, type, date);
        Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
        return bucket.fetch(timelineKey, Timeline.class).execute();
    }

    private String generateKeyFromMsg(Msg msg, Timeline.TimelineType type) {
        String owner = getOwner(msg, type);
        String dateString = msg.Created.substring(0, 10);
        return generateKey(owner, type, dateString);
    }

    private String getOwner(Msg msg, Timeline.TimelineType type) {
        if(type == Timeline.TimelineType.Inbox)
            return msg.Recipient;
        else
            return msg.Sender;
    }

    private String generateKey(String ownerUsername, Timeline.TimelineType type, Date date) {
        String dateString = getIso8601DateStringFromDate(date);
        return generateKey(ownerUsername, type, dateString);
    }

    private String generateKey(String ownerUsername, Timeline.TimelineType type, String dateString) {
        return ownerUsername + "_" + type.toString() + "_" + dateString;
    }

    private String getIso8601DateStringFromDate(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(tz);
        return df.format(date);
    }


}
