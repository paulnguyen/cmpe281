package com.basho.msgy.Repositories;

import com.basho.msgy.Models.Msg;
import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;

public class MsgRepository {

    static final String BUCKET_NAME = "Msgs";
    protected IRiakClient client;

    public MsgRepository(IRiakClient client) {

        this.client = client;
    }

    public Msg get(String msgKey) throws RiakRetryFailedException {
        Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
        return bucket.fetch(msgKey, Msg.class).execute();
    }

    public String save(Msg msg) throws RiakRetryFailedException {
        String msgKey = generateKey(msg);
        Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
        bucket.store(msgKey, msg).execute();
        return msgKey;
    }

    private String generateKey(Msg msg) {
        return msg.Sender + "_" + msg.Created;
    }
}
