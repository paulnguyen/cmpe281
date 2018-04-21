namespace Ch02_Schemas_and_Indexes
{
    using System;

    public class Customer
    {
        private readonly uint id;
        private readonly string name;
        private readonly string address;
        private readonly string city;
        private readonly string state;
        private readonly string zip;
        private readonly string phone;
        private readonly DateTime createdDate;

        public Customer(
            uint id, string name,
            string address, string city, string state, string zip,
            string phone, DateTime createdDate)
        {
            if (id == default(uint))
            {
                throw new ArgumentOutOfRangeException("id", "id must be greater than 0");
            }
            this.id = id;

            if (string.IsNullOrWhiteSpace(name))
            {
                throw new ArgumentNullException("name");
            }
            this.name = name;

            if (string.IsNullOrWhiteSpace(address))
            {
                throw new ArgumentNullException("address");
            }
            this.address = address;

            if (string.IsNullOrWhiteSpace(city))
            {
                throw new ArgumentNullException("city");
            }
            this.city = city;

            if (string.IsNullOrWhiteSpace(state))
            {
                throw new ArgumentNullException("state");
            }
            this.state = state;

            if (string.IsNullOrWhiteSpace(zip))
            {
                throw new ArgumentNullException("zip");
            }
            this.zip = zip;

            if (string.IsNullOrWhiteSpace(phone))
            {
                throw new ArgumentNullException("phone");
            }
            this.phone = phone;

            this.createdDate = createdDate;
        }

        public uint Id
        {
            get { return id; }
        }

        public string Name
        {
            get { return name; }
        }

        public string Address
        {
            get { return address; }
        }

        public string City
        {
            get { return city; }
        }

        public string State
        {
            get { return state; }
        }

        public string Zip
        {
            get { return zip; }
        }

        public string Phone
        {
            get { return phone; }
        }

        public DateTime CreatedDate
        {
            get { return createdDate; }
        }
    }
}
