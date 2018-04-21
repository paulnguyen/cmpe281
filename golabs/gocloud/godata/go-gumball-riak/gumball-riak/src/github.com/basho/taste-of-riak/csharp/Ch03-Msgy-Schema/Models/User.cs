namespace Ch03_Msgy_Schema.Models
{
    using System;

    public class User : IModel
    {
        private readonly string userName;
        private readonly string fullName;
        private readonly string email;

        public User(string userName, string fullName, string email)
        {
            if (string.IsNullOrWhiteSpace(userName))
            {
                throw new ArgumentNullException("userName", "userName must not be null, empty or all whitespace");
            }
            this.userName = userName;

            if (string.IsNullOrWhiteSpace(fullName))
            {
                throw new ArgumentNullException("fullName", "fullName must not be null, empty or all whitespace");
            }
            this.fullName = fullName;

            if (string.IsNullOrWhiteSpace(email))
            {
                throw new ArgumentNullException("email", "email must not be null, empty or all whitespace");
            }
            this.email = email;
        }

        public string ID
        {
            get { return UserName; }
        }

        public string UserName
        {
            get { return userName; }
        }

        public string FullName
        {
            get { return fullName; }
        }
    }
}
