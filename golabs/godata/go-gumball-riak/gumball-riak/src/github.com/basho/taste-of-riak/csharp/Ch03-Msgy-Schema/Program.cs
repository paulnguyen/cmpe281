namespace Ch03_Msgy_Schema
{
    using System;
    using System.Linq;
    using Models;
    using Repositories;
    using RiakClient;

    static class Program
    {
        static void Main(string[] args)
        {
            using (IRiakEndPoint endpoint = RiakCluster.FromConfig("riakConfig"))
            {
                IRiakClient client = endpoint.CreateClient();
                UserRepository userRepo = new UserRepository(client);
                MsgRepository msgRepo = new MsgRepository(client);
                TimelineRepository timelineRepo = new TimelineRepository(client);
                TimelineManager timelineMgr = new TimelineManager(timelineRepo, msgRepo);

                // Create and save users
                var marleen = new User("marleenmgr", "Marleen Manager", "marleen.manager@basho.com");
                var joe = new User("joeuser", "Joe User", "joe.user@basho.com");
                userRepo.Save(marleen);
                userRepo.Save(joe);

                // Create new Msg, post to timelines
                Msg msg = new Msg(marleen.UserName, joe.UserName, "Welcome to the company!");
                timelineMgr.PostMsg(msg);

                // Get Joe's inbox for today, get first message
                Timeline joesInboxToday = timelineMgr.GetTimeline(joe.UserName, Timeline.TimelineType.Inbox, DateTime.UtcNow);
                Msg joesFirstMsg = msgRepo.Get(joesInboxToday.MsgKeys.First());

                Console.WriteLine("From: " + joesFirstMsg.Sender);
                Console.WriteLine("Msg : " + joesFirstMsg.Text);
            }
        }
    }
}
