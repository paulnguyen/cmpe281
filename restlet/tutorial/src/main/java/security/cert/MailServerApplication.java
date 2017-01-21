

package security.cert ;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.security.CertificateAuthenticator;
import org.restlet.routing.Router;
import org.restlet.security.Authenticator;
import org.restlet.util.Series;
import org.restlet.security.ChallengeAuthenticator;
import org.restlet.security.MapVerifier;
import org.restlet.data.ChallengeScheme;

/**
 * Application using authentication based on client SSL certificates to protect
 * its resources.
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

        // 1) Configure the SSL key store
        parameters.add("keystorePath", "certs/server-keystore.jks");
        parameters.add("keystorePassword", "password");
        parameters.add("keystoreType", "JKS");
        parameters.add("keyPassword", "password");

        // 2) Ask client for an SSL certificate
        parameters.add("wantClientAuthentication", "true");
        //parameters.add("needClientAuthentication", "true");

        // 3) Configure the SSL trust store
        parameters.add("truststorePath", "certs/server-truststore.jks");
        parameters.add("truststorePassword", "password");
        parameters.add("truststoreType", "JKS");

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

        // Create the authenticator based on client TLS certificates
        Authenticator authenticator = new CertificateAuthenticator(getContext());
        authenticator.setNext(router);

        return authenticator;
    }
}
