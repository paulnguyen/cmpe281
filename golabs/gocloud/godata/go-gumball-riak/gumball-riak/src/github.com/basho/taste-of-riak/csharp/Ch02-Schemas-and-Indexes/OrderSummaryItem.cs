namespace Ch02_Schemas_and_Indexes
{
    using System;

    public class OrderSummaryItem
    {
        private readonly uint orderId;
        private readonly decimal total;
        private readonly DateTime orderDate;

        public OrderSummaryItem(Order parentOrder)
        {
            if (parentOrder == null)
            {
                throw new ArgumentNullException("parentOrder");
            }

            this.orderId = parentOrder.Id;
            this.total = parentOrder.Total;
            this.orderDate = parentOrder.OrderDate;
        }

        public uint OrderId
        {
            get { return orderId; }
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
