
package firstapplication.client ;

import org.restlet.data.MediaType;
import org.restlet.resource.ClientResource;
import firstapplication.common.* ;

public class TestClient {

	public static void main(String[] args) {

		Contact contact ;

		// Initialize the resource proxy.
		ClientResource cr = new ClientResource( "http://localhost:8080/contacts/123" );
		// Workaround for GAE servers to prevent chunk encoding
		//cr.setRequestEntityBuffering(true);
		cr.accept(MediaType.APPLICATION_JSON);

		ContactResource resource = cr.wrap(ContactResource.class);

		// Get the remote contact
		System.out.println( "Get...") ;
		contact = resource.retrieve();
		if (contact != null) {
			System.out.println("firstname: " + contact.getFirstName());
			System.out.println(" lastname: " + contact.getLastName());
			System.out.println("      age: " + contact.getAge());
		}

		// Update the contact
		System.out.println( "Put...") ;
		contact.setFirstName("Roy");
		resource.store(contact);

		// Get the remote contact
		System.out.println( "Get...") ;
		contact = resource.retrieve();
		if (contact != null) {
			System.out.println("firstname: " + contact.getFirstName());
			System.out.println(" lastname: " + contact.getLastName());
			System.out.println("      age: " + contact.getAge());
		}

	}

}
