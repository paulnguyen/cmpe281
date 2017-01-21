

package security.secret ;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

/**
 * Mail client retrieving a mail then storing it again on the same resource.
 */
public class MailClient {

    public static void main(String[] args) throws Exception {
        // Create and configure HTTPS client
        Client client = new Client(new Context(), Protocol.HTTPS);
        Series<Parameter> parameters = client.getContext().getParameters();
        parameters.add("truststorePath", "certs/client-truststore.jks" );
        parameters.add("truststorePassword", "password");
        parameters.add("truststoreType", "JKS");

        // Create and configure client resource
        ClientResource clientResource = new ClientResource(
                "https://localhost:8183/accounts/chunkylover53/mails/123");
        clientResource.setNext(client);

        // Preemptively configure the authentication credentials
        ChallengeResponse authentication = new ChallengeResponse(
                ChallengeScheme.HTTP_BASIC, "chunkylover53", "pwd");
        clientResource.setChallengeResponse(authentication);

        // Communicate with remote resource
        MailResource mailClient = clientResource.wrap(MailResource.class);
        Mail m =  mailClient.retrieve() ;
        System.out.println( "Subject: " + m.getSubject() ) ;
        System.out.println( "Content: " + m.getContent() ) ;

        // Store HTTPS client
        client.stop();
    }

}
