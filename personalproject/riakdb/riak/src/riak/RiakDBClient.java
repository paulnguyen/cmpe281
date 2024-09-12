package riak;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.DeleteValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.commands.kv.UpdateValue;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.RiakNode;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

import java.awt.List;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;

public class RiakDBClient {

	public static class Person{
		public String key;
		public String email;
		public String firstName;
		public String lastName;
		
	}

    // This will create a client object that we can use to interact with Riak
    private static RiakCluster setUpCluster() throws UnknownHostException {
       
       
        RiakNode.Builder nodeTemplate = new RiakNode.Builder()
                .withMinConnections(3)
                .withMaxConnections(10);

        // Create a list of 3 nodes to connect to.
        LinkedList<RiakNode> nodes = new LinkedList<RiakNode>();
        nodes.add(nodeTemplate.withRemoteAddress("3.210.184.4").withRemotePort(8087).build());
        nodes.add(nodeTemplate.withRemoteAddress("18.232.54.134").withRemotePort(8087).build());
        nodes.add(nodeTemplate.withRemoteAddress("18.205.38.137").withRemotePort(8087).build());
        nodes.add(nodeTemplate.withRemoteAddress("54.174.162.44").withRemotePort(8087).build());
        nodes.add(nodeTemplate.withRemoteAddress("3.93.184.33").withRemotePort(8087).build());

        // Increase the # of execution attempts (retries) to 5, from the default of 3
        RiakCluster myCluster = RiakCluster.builder(nodes)
                .withExecutionAttempts(5)
                .build();

        // RiakCluster must be started before given to RiakClient ctor.
        myCluster.start();
        return myCluster;
    }

    public static void main( String[] args ) {
        try {
        	RiakCluster cluster = setUpCluster();
        	RiakClient client = new RiakClient(cluster);
           for(int i=0;i<60;i++) {
            	//cerating bucket
        	   Person p = new Person();
        	   p.key = "key_"+i;
        	   p.email = "saliha.mehboob_"+i+"@sjsu.edu";
        	   p.firstName = "saliha"+i;
        	   p.lastName = "mehboob"+i;
            	Namespace quotesBucket = new Namespace("personalData");
            	Location quoteObjectLocation = new Location(quotesBucket, p.key);
            	StoreValue storeOp = new StoreValue.Builder(p)
                    .withLocation(quoteObjectLocation)
                    .build();
            	//storing in riak
            	StoreValue.Response response = client.execute(storeOp);
            	System.out.println("value stored for key "+ response.getLocation().getKeyAsString());
            	Thread.sleep(1000);
            }
        //    cluster.shutdown();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
