package com.basho.msgy.Repositories;

import com.basho.msgy.Models.User;
import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;

public class UserRepository {
    static final String BUCKET_NAME = "Users";
    protected IRiakClient client;

    public UserRepository(IRiakClient client) {
        this.client = client;
    }

    public void save(User user) throws RiakRetryFailedException {
        Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
        bucket.store(user).execute();
    }

    public User get(String UserName) throws RiakRetryFailedException {
        Bucket bucket = client.fetchBucket(BUCKET_NAME).execute();
        return bucket.fetch(UserName, User.class).execute();
    }
}
