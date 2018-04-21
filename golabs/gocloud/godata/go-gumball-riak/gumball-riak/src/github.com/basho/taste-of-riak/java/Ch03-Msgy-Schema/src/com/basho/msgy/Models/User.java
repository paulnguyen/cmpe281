package com.basho.msgy.Models;

import com.basho.riak.client.convert.RiakKey;

public class User {
    @RiakKey
    public String UserName;
    public String FullName;
    public String Email;

    public User() { }

    public User(String userName, String fullName, String email) {
        this.UserName = userName;
        this.FullName = fullName;
        this.Email = email;
    }
}
