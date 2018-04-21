namespace Ch03_Msgy_Schema.Models
{
    using System;

    public class Msg : IModel
    {
        private readonly string sender;
        private readonly string recipient;
        private readonly string text;
        private readonly DateTime created;

        public Msg(string sender, string recipient, string text)
        {
            if (string.IsNullOrWhiteSpace(sender))
            {
                throw new ArgumentNullException("sender", "sender can't be null, empty or contain only whitespace");
            }
            this.sender = sender;

            if (string.IsNullOrWhiteSpace(recipient))
            {
                throw new ArgumentNullException("recipient", "recipient can't be null, empty or contain only whitespace");
            }
            this.recipient = recipient;

            if (text == null)
            {
                throw new ArgumentNullException("text", "text can't be null");
            }
            this.text = text;

            this.created = DateTime.UtcNow;
        }

        public string ID
        {
            get { return string.Concat(Sender, "_", Created.AsISO8601()); }
        }

        public string Sender
        {
            get { return sender; }
        }

        public string Recipient
        {
            get { return recipient; }
        }

        public string Text
        {
            get { return text; }
        }

        public DateTime Created
        {
            get { return created; }
        }
    }
}
