
package firstapplication.server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.Component;
import org.restlet.data.Protocol;


public class TestServerApplication extends Application {

  public static void main(String[] args) throws Exception {
        Component c = new Component();
        c.getServers().add( Protocol.HTTP, 8080 ) ;
        c.getDefaultHost().attach( new TestServerApplication() ) ;
        c.start();
    }

    @Override
    public Restlet createInboundRoot() {
    	Router router = new Router(getContext());
       	router.attach("/contacts/123", ContactServerResource.class);
        return router;
    }

}
