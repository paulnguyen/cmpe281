namespace Ch03_Msgy_Schema
{
    using System;
    using Models;
    using Repositories;
    using RiakClient;

    public class TimelineManager
    {
        private readonly IRepository<Timeline> timelineRepository;
        private readonly IRepository<Msg> msgRepository;

        public TimelineManager(IRepository<Timeline> timelineRepository, IRepository<Msg> msgRepository)
        {
            if (timelineRepository == null)
            {
                throw new ArgumentNullException("timelineRepository");
            }
            this.timelineRepository = timelineRepository;

            if (msgRepository == null)
            {
                throw new ArgumentNullException("msgRepository");
            }
            this.msgRepository = msgRepository;
        }

        public void PostMsg(Msg msg)
        {
            string msgKey = msgRepository.Save(msg);

            // Post to recipient's Inbox timeline
            AddToTimeline(msg, Timeline.TimelineType.Inbox, msgKey);

            // Post to sender's Sent timeline
            AddToTimeline(msg, Timeline.TimelineType.Sent, msgKey);
        }

        public Timeline GetTimeline(string ownerUsername, Timeline.TimelineType type, DateTime date)
        {
            string timelineKey = GenerateKey(ownerUsername, type, date);
            return timelineRepository.Get(timelineKey);
        }

        private void AddToTimeline(Msg msg, Timeline.TimelineType type, string msgKey)
        {
            string timelineKey = GenerateKeyFromMsg(msg, type);

            Timeline timeline = timelineRepository.Get(key: timelineKey, notFoundOK: true);
            if (timeline != null)
            {
                timeline = AddToExistingTimeline(timeline, msgKey);
            }
            else
            {
                timeline = CreateNewTimeline(timelineKey, msgKey);
            }

            timelineRepository.Save(timeline);
        }

        private static Timeline AddToExistingTimeline(Timeline timeline, string msgKey)
        {
            timeline.AddMsg(msgKey);
            return timeline;
        }

        private static Timeline CreateNewTimeline(string timelineKey, string msgKey)
        {
            var newTimeline = new Timeline(timelineKey);
            newTimeline.AddMsg(msgKey);
            return newTimeline;
        }

        private string GenerateKeyFromMsg(Msg msg, Timeline.TimelineType type)
        {
            string owner = GetOwner(msg, type);
            return GenerateKey(owner, type, msg.Created);
        }

        private static string GetOwner(Msg msg, Timeline.TimelineType type)
        {
            switch (type)
            {
                case Timeline.TimelineType.Inbox:
                    return msg.Recipient;
                case Timeline.TimelineType.Sent:
                    return msg.Sender;
            }

            return msg.Recipient;
        }

        private static string GenerateKey(string ownerUsername, Timeline.TimelineType type, DateTime date)
        {
            string dateString = date.ToString("yyyy-MM-dd");
            return ownerUsername + "_" + type.ToString() + "_" + dateString;
        }
    }
}
