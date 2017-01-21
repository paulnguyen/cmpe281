package api ;

import org.json.* ;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.* ;
import gumball.GumballMachine ;
import java.io.IOException ;
import org.restlet.ext.jackson.* ;


public class GumballResource extends ServerResource {

    GumballMachineAPI machine = new GumballMachineAPI() ;

    @Get
    public Representation get_action() throws JSONException {

        Gumball g = new Gumball() ;
        g.setCount( machine.getCount() ) ;
        g.setBanner( machine.toString() ) ;
        g.setState( machine.getStateString() ) ;

        return new JacksonRepresentation<Gumball>(g) ;
    }


    @Post
    public Representation post_action (Representation rep) throws IOException {
        JacksonRepresentation<GumballCommand> cmdRep = 
            new JacksonRepresentation<GumballCommand> ( rep, GumballCommand.class ) ;

        GumballCommand cmd = cmdRep.getObject() ;
        String action = cmd.getAction() ;
        String payment = cmd.getPayment() ;
        System.out.println( "action: " + action + "  payment: " + payment ) ;

        if ( action.equals( "place-order") && payment.equals( "quarter" ) )
            machine.placeOrder() ;

        String state = machine.getStateString() ;
        cmd.setResult( state ) ;

        return new JacksonRepresentation<GumballCommand>(cmd) ;

    }


}

