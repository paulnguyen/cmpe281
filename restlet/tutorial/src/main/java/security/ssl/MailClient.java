
package security.ssl ;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

/**
 * Mail client retrieving a mail then storing it again on the same resource.
 */
public class MailClient {

    public static void main(String[] args) throws Exception {
        Client client = new Client(new Context(), Protocol.HTTPS);
        Series<Parameter> parameters = client.getContext().getParameters();
        parameters.add("truststorePath", "certs/client-truststore.jks");
        parameters.add("truststorePassword", "password");
        parameters.add("truststoreType", "JKS");

        ClientResource clientResource = new ClientResource(
                "https://localhost:8183/accounts/chunkylover53/mails/123");
        clientResource.setNext(client);
        MailResource mailClient = clientResource.wrap(MailResource.class);
        Mail m =  mailClient.retrieve() ;
        System.out.println( "Subject: " + m.getSubject() ) ;
        System.out.println( "Content: " + m.getContent() ) ;

        client.stop();
    }

}
