namespace Ch02_Schemas_and_Indexes
{
    using System;
    using System.Collections.Generic;

    public class Order
    {
        private readonly uint id;
        private readonly uint customerId;
        private readonly uint salesPersonId;
        private readonly DateTime orderDate;
        private readonly IList<Item> items = new List<Item>();

        private decimal total;

        public Order(uint id, uint customerId, uint salesPersonId, DateTime orderDate)
        {
            if (id == default(uint))
            {
                throw new ArgumentOutOfRangeException("id", "id must be greater than 0");
            }
            this.id = id;

            if (customerId == default(uint))
            {
                throw new ArgumentOutOfRangeException("customerId", "customerId must be greater than 0");
            }
            this.customerId = customerId;

            this.salesPersonId = salesPersonId;
            this.orderDate = orderDate;
        }

        public uint Id
        {
            get { return id; }
        }

        public uint CustomerId
        {
            get { return customerId; }
        }

        public uint SalesPersonId
        {
            get { return salesPersonId; }
        }

        public IEnumerable<Item> Items
        {
            get { return items; }
        }

        public void AddItem(Item item)
        {
            items.Add(item);
            total += item.Price;
        }

        public decimal Total
        {
            get { return total; }
        }

        public DateTime OrderDate
        {
            get { return orderDate; }
        }
    }
}
