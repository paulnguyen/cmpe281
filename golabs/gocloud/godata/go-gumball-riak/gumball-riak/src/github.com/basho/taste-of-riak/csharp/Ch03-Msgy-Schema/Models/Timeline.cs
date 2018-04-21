namespace Ch03_Msgy_Schema.Models
{
    using System;
    using System.Collections.Generic;

    public class Timeline : IModel
    {
        private readonly string id;
        private readonly IList<string> msgKeys = new List<string>();

        public Timeline(string id)
        {
            if (string.IsNullOrWhiteSpace(id))
            {
                throw new ArgumentNullException("id", "id must not be empty or null");
            }
            this.id = id;
        }

        public string ID
        {
            get { return id; }
        }

        public IEnumerable<string> MsgKeys
        {
            get { return msgKeys; }
        }

        public void AddMsg(string msgKey)
        {
            if (string.IsNullOrWhiteSpace(msgKey))
            {
                throw new ArgumentNullException("msgKey", "msgKey must not be null, empty or whitespace");
            }
            msgKeys.Add(msgKey);
        }

        public enum TimelineType
        {
            Inbox,
            Sent
        }
    }
}
