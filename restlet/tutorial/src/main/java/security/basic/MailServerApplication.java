

package security.basic ;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;
import org.restlet.util.Series;

/**
 * Application using HTTP Basic authentication to protect its resources.
 */
public class MailServerApplication extends Application {

    /**
     * Launches the application with an HTTP server.
     * 
     * @param args
     *            The arguments.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Component mailServer = new Component();

        // Configure the HTTPS server with the SSL certificates
        Server server = mailServer.getServers().add(Protocol.HTTPS, 8183);
        Series<Parameter> parameters = server.getContext().getParameters();
        parameters.add("keystorePath", "certs/server-keystore.jks");
        parameters.add("keystorePassword", "password");
        parameters.add("keystoreType", "JKS");
        parameters.add("keyPassword", "password");

        mailServer.getDefaultHost().attach(new MailServerApplication());
        mailServer.start();
    }

    /**
     * Creates a root Router to dispatch call to server resources.
     */
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/accounts/{accountId}/mails/{mailId}",
                MailServerResource.class);

        // Create the HTTP Basic authenticator
        ChallengeAuthenticator authenticator = new ChallengeAuthenticator(
                getContext(), ChallengeScheme.HTTP_BASIC, "My Realm");

        // Set the credentials verifier
        MapVerifier verifier = new MapVerifier();
        verifier.getLocalSecrets().put("chunkylover53", "pwd".toCharArray());
        authenticator.setVerifier(verifier);

        // Chain the authenticator with the router
        authenticator.setNext(router);
        return authenticator;
    }
}
