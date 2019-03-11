import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
public class MongoDbClient {
	 @SuppressWarnings("unchecked")
	public static void main(String[] args) {

		    try {
		    	MongoCredential credential = MongoCredential.createCredential("admin", "admin", "admin123".toCharArray());
		    	MongoClient mongoClient = new MongoClient(new ServerAddress("54.145.195.42", 27017), Arrays.asList(credential));
		    	MongoDatabase db = mongoClient.getDatabase("test");
	            MongoCollection<Document> collection = db.getCollection("products");

			System.out.println("BasicDBObject example...");
			ArrayList<Document> documents = new ArrayList<Document>();
			for(int i=1;i<5;i++) {
				Document document = new Document();
				document.put("id","pid"+i);
				document.put("name","product_"+i);
				documents.add(document);
				System.out.println("inserting key "+i+ " with value "+i );
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			collection.insertMany(documents);
		    }catch (MongoException e) {
			e.printStackTrace();
		    }
		    
		    }

}
