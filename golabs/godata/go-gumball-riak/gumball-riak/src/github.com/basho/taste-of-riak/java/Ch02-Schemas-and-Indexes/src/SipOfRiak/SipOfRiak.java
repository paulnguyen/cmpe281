package SipOfRiak;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.query.indexes.BinIndex;
import com.basho.riak.client.query.indexes.IntIndex;

import java.util.ArrayList;
import java.util.List;

public class SipOfRiak {

    public static void main(String[] args) throws RiakException {

        System.out.println("Creating Data");
        Customer customer = createCustomer();
        ArrayList<Order> orders = createOrders();
        OrderSummary orderSummary = createOrderSummary(orders);


        System.out.println("Starting Client");
        IRiakClient client = RiakFactory.pbcClient("127.0.0.1", 10017);


        System.out.println("Creating Buckets");
        Bucket customersBucket = client.fetchBucket("Customers").lazyLoadBucketProperties().execute();
        Bucket ordersBucket = client.fetchBucket("Orders").lazyLoadBucketProperties().execute();
        Bucket orderSummariesBucket = client.fetchBucket("OrderSummaries").lazyLoadBucketProperties().execute();

        System.out.println("Storing Data");
        customersBucket.store(String.valueOf(customer.CustomerId), customer).execute();
        for (Order order : orders) {
            ordersBucket.store(String.valueOf(order.OrderId), order).execute();
        }
        orderSummariesBucket.store(String.valueOf(orderSummary.CustomerId), orderSummary).execute();


        System.out.println("Fetching related data by shared key");
        String key = "1";
        String fetchedCust = customersBucket.fetch(key).execute().getValueAsString();
        String fetchedOrdSum = orderSummariesBucket.fetch(key).execute().getValueAsString();
        System.out.format("Customer     1: %s\n", fetchedCust);
        System.out.format("OrderSummary 1: %s\n", fetchedOrdSum);


        System.out.println("Adding Index Data");
        IRiakObject riakObj = ordersBucket.fetch("1").execute();
        riakObj.addIndex("SalespersonId", 9000);
        riakObj.addIndex("OrderDate", "2013-10-01");
        ordersBucket.store(riakObj).execute();

        IRiakObject riakObj2 = ordersBucket.fetch("2").execute();
        riakObj2.addIndex("SalespersonId", 9001);
        riakObj2.addIndex("OrderDate", "2013-10-15");
        ordersBucket.store(riakObj2).execute();

        IRiakObject riakObj3 = ordersBucket.fetch("3").execute();
        riakObj3.addIndex("SalespersonId", 9000);
        riakObj3.addIndex("OrderDate", "2013-11-03");
        ordersBucket.store(riakObj3).execute();

        System.out.println("Index Queries");
        // Query for orders where the SalespersonId index is set to 9000
        List<String> janesOrders = ordersBucket.fetchIndex(IntIndex.named("SalespersonId"))
                                               .withValue(9000).execute();

        System.out.format("Jane's Orders: %s\n", StringUtil.Join(", ", janesOrders));


        // Query for orders where the OrderDate index is between 2013-10-01 and 2013-10-31
        List<String> octoberOrders = ordersBucket.fetchIndex(BinIndex.named("OrderDate"))
                                                 .from("2013-10-01").to("2013-10-31").execute();

        System.out.format("October's Orders: %s\n", StringUtil.Join(", ", octoberOrders));

        // Uncomment if you would like to clean up test data
        /*
        customersBucket.delete("1").execute();

        ordersBucket.delete("1").execute();
        ordersBucket.delete("2").execute();
        ordersBucket.delete("3").execute();

        orderSummariesBucket.delete("1").execute();
        */

        client.shutdown();
    }

    private static Customer createCustomer() {
        Customer customer = new Customer();
        customer.CustomerId = 1;
        customer.Name = "John Smith";
        customer.Address = "123 Main Street";
        customer.City = "Columbus";
        customer.State = "Ohio";
        customer.Zip = "43210";
        customer.Phone = "+1-614-555-5555";
        customer.CreatedDate = "2013-10-01 14:30:26";
        return customer;
    }

    private static ArrayList<Order> createOrders() {
        ArrayList<Order> orders = new ArrayList<Order>();

        Order order1 = new Order();
        order1.OrderId = 1;
        order1.CustomerId = 1;
        order1.SalespersonId = 9000;
        order1.Items.add(
                new Item("TCV37GIT4NJ",
                        "USB 3.0 Coffee Warmer",
                        15.99));
        order1.Items.add(
                new Item("PEG10BBF2PP",
                        "eTablet Pro; 24GB; Grey",
                        399.99));
        order1.Total = 415.98;
        order1.OrderDate = "2013-10-01 14:42:26";
        orders.add(order1);

        Order order2 = new Order();
        order2.OrderId = 2;
        order2.CustomerId = 1;
        order2.SalespersonId = 9001;
        order2.Items.add(
                new Item("OAX19XWN0QP",
                        "GoSlo Digital Camera",
                        359.99));
        order2.Total = 359.99;
        order2.OrderDate = "2013-10-15 16:43:16";
        orders.add(order2);

        Order order3 = new Order();
        order3.OrderId = 3;
        order3.CustomerId = 1;
        order3.SalespersonId = 9000;
        order3.Items.add(
                new Item("WYK12EPU5EZ",
                        "Call of Battle = Goats - Gamesphere 4",
                        69.99));
        order3.Items.add(
                new Item("TJB84HAA8OA",
                        "Bricko Building Blocks",
                        4.99));
        order3.Total = 74.98;
        order3.OrderDate = "2013-11-03 17:45:28";
        orders.add(order3);
        return orders;
    }

    private static OrderSummary createOrderSummary(ArrayList<Order> orders) {
        OrderSummary orderSummary = new OrderSummary();
        orderSummary.CustomerId = 1;
        for(Order order: orders)
        {
            orderSummary.Summaries.add(new OrderSummaryItem(order));
        }
        return orderSummary;
    }

}
