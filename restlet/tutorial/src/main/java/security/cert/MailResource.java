

package security.cert ;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

/**
 * Annotated mail resource interface
 */
public interface MailResource {

    @Get
    public Mail retrieve();

    @Put
    public void store(Mail mail);

}
