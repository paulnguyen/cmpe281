namespace Ch02_Schemas_and_Indexes
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using RiakClient;
    using RiakClient.Models;
    using RiakClient.Models.Index;

    static class Program
    {
        const string customersBucketName = "Customers";

        const string ordersBucketName = "Orders";
        const string ordersSalesPersonIdIndexName = "SalesPersonId";
        const string ordersOrderDateIndexName = "OrderDate";

        const string orderSummariesBucketName = "OrderSummaries";

        static void Main(string[] args)
        {
            Console.WriteLine("Creating Data");
            Customer customer = CreateCustomer();
            IEnumerable<Order> orders = CreateOrders(customer);
            OrderSummary orderSummary = CreateOrderSummary(customer, orders);


            Console.WriteLine("Starting Client");
            using (IRiakEndPoint endpoint = RiakCluster.FromConfig("riakConfig"))
            {
                IRiakClient client = endpoint.CreateClient();

                Console.WriteLine("Storing Data");

                client.Put(ToRiakObject(customer));

                foreach (Order order in orders)
                {
                    // NB: this adds secondary index data as well
                    client.Put(ToRiakObject(order));
                }

                client.Put(ToRiakObject(orderSummary));

                Console.WriteLine("Fetching related data by shared key");
                string key = "1";

                var result = client.Get(customersBucketName, key);
                CheckResult(result);
                Console.WriteLine("Customer     1: {0}\n", GetValueAsString(result));

                result = client.Get(orderSummariesBucketName, key);
                CheckResult(result);
                Console.WriteLine("OrderSummary 1: {0}\n", GetValueAsString(result));

                Console.WriteLine("Index Queries");

                // Query for order keys where the SalesPersonId index is set to 9000
                var riakIndexId = new RiakIndexId(ordersBucketName, ordersSalesPersonIdIndexName);
                RiakResult<RiakIndexResult> indexRiakResult = client.GetSecondaryIndex(riakIndexId, 9000); // NB: *must* use 9000 as integer here.
                CheckResult(indexRiakResult);
                RiakIndexResult indexResult = indexRiakResult.Value;
                Console.WriteLine("Jane's orders (key values): {0}", string.Join(", ", indexResult.IndexKeyTerms.Select(ikt => ikt.Key)));

                // Query for orders where the OrderDate index is between 2013-10-01 and 2013-10-31
                riakIndexId = new RiakIndexId(ordersBucketName, ordersOrderDateIndexName);
                indexRiakResult = client.GetSecondaryIndex(riakIndexId, "2013-10-01", "2013-10-31"); // NB: *must* use strings here.
                CheckResult(indexRiakResult);
                indexResult = indexRiakResult.Value;
                Console.WriteLine("October orders (key values): {0}", string.Join(", ", indexResult.IndexKeyTerms.Select(ikt => ikt.Key)));
            }
        }

        private static string GetValueAsString(RiakResult<RiakObject> result)
        {
            string rv = string.Empty;

            if (result.IsSuccess)
            {
                RiakObject riakObject = result.Value;
                rv = Encoding.UTF8.GetString(riakObject.Value);
            }
            else
            {
                rv = "ERROR";
            }

            return rv;
        }

        private static void CheckResult<T>(RiakResult<T> result)
        {
            if (!result.IsSuccess)
            {
                Console.Error.WriteLine("Error: {0}", result.ErrorMessage);
                Environment.Exit(1);
            }
        }

        private static RiakObject ToRiakObject(Customer customer)
        {
            var customerRiakObjectId = new RiakObjectId(customersBucketName, customer.Id.ToString());
            return new RiakObject(customerRiakObjectId, customer);
        }

        private static RiakObject ToRiakObject(Order order)
        {
            var orderRiakObjectId = new RiakObjectId(ordersBucketName, order.Id.ToString());
            var riakObject = new RiakObject(orderRiakObjectId, order);

            IntIndex salesPersonIdIndex = riakObject.IntIndex(ordersSalesPersonIdIndexName);
            salesPersonIdIndex.Add(order.SalesPersonId.ToString());

            BinIndex orderDateIndex = riakObject.BinIndex(ordersOrderDateIndexName);
            orderDateIndex.Add(order.OrderDate.ToString("yyyy-MM-dd"));

            return riakObject;
        }

        private static RiakObject ToRiakObject(OrderSummary orderSummary)
        {
            var orderSummaryRiakObjectId = new RiakObjectId(orderSummariesBucketName, orderSummary.CustomerId.ToString());
            return new RiakObject(orderSummaryRiakObjectId, orderSummary);
        }

        public static Customer CreateCustomer()
        {
            return new Customer(
                id: 1,
                name: "John Smith",
                address: "123 Main Street",
                city: "Columbus",
                state: "Ohio",
                zip: "43210",
                phone: "+1-614-555-5555",
                createdDate: DateTime.Parse("2013-10-01 14:30:26")
            );
        }

        private static IEnumerable<Order> CreateOrders(Customer customer)
        {
            var orders = new List<Order>();

            var order1 = new Order(
                id: 1,
                customerId: customer.Id,
                salesPersonId: 9000,
                orderDate: DateTime.Parse("2013-10-01 14:42:26")
                );
            order1.AddItem(new Item("TCV37GIT4NJ", "USB 3.0 Coffee Warmer", 15.99m));
            order1.AddItem(new Item("PEG10BBF2PP", "eTablet Pro; 24GB; Grey", 399.99m));
            orders.Add(order1);

            var order2 = new Order(
                id: 2,
                customerId: customer.Id,
                salesPersonId: 9001,
                orderDate: DateTime.Parse("2013-10-15 16:43:16")
            );
            order2.AddItem(new Item("OAX19XWN0QP", "GoSlo Digital Camera", 359.99m));
            orders.Add(order2);

            var order3 = new Order(
                id: 3,
                customerId: customer.Id,
                salesPersonId: 9000,
                orderDate: DateTime.Parse("2013-11-03 17:45:28")
            );
            order3.AddItem(new Item("WYK12EPU5EZ", "Call of Battle = Goats - Gamesphere 4", 69.99m));
            order3.AddItem(new Item("TJB84HAA8OA", "Bricko Building Blocks", 4.99m));
            orders.Add(order3);

            return orders;
        }

        private static OrderSummary CreateOrderSummary(Customer customer, IEnumerable<Order> orders)
        {
            var orderSummary = new OrderSummary(customerId: customer.Id);

            foreach (Order order in orders)
            {
                orderSummary.AddSummaryItem(new OrderSummaryItem(order));
            }

            return orderSummary;
        }
    }
}
