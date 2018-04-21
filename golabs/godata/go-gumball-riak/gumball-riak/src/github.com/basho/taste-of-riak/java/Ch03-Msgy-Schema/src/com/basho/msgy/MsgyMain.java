package com.basho.msgy;

import com.basho.msgy.Models.Msg;
import com.basho.msgy.Models.Timeline;
import com.basho.msgy.Models.User;
import com.basho.msgy.Repositories.MsgRepository;
import com.basho.msgy.Repositories.TimelineRepository;
import com.basho.msgy.Repositories.UserRepository;
import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;

import java.util.Date;

public class MsgyMain {

    public static void main(String[] args) throws RiakException {
        // Setup our repositories
        IRiakClient client = RiakFactory.pbcClient("127.0.0.1", 10017);

        UserRepository userRepo = new UserRepository(client);
        MsgRepository msgRepo = new MsgRepository(client);
        TimelineRepository timelineRepo = new TimelineRepository(client);

        // Create and save users
        User marleen = new User("marleenmgr",
                "Marleen Manager",
                "marleen.manager@basho.com");

        User joe = new User("joeuser",
                "Joe User",
                "joe.user@basho.com");

        userRepo.save(marleen);
        userRepo.save(joe);

        // Create new Msg, post to timelines
        Msg msg = Msg.createNew(marleen.UserName,
                joe.UserName,
                "Welcome to the company!");

        timelineRepo.postMsg(msg);


        // Get Joe's inbox for today, get first message
        Timeline joesInboxToday = timelineRepo.getTimeline(joe.UserName,
                                                           Timeline.TimelineType.Inbox,
                                                           new Date());

        Msg joesFirstMsg = msgRepo.get(joesInboxToday.Msgs.get(0));

        System.out.println("From: " + joesFirstMsg.Sender);
        System.out.println("Msg : " + joesFirstMsg.Text);
        System.out.println("");

        client.shutdown();
    }
}
