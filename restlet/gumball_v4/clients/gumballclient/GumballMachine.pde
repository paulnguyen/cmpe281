
import org.json.JSONObject ;
import org.restlet.resource.*;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;

public class GumballMachine {
  
  private String URL = "http://localhost:8080/v4/gumball" ;
  private String payment = "" ;
  ClientResource client ; 

  public GumballMachine() {
    client = new ClientResource( URL ); 
  }

  public void insertQuarter() {
    this.payment = "quarter" ;
  }
  
  public void turnCrank() {
    JSONObject json_turn_crank = new JSONObject();
    json_turn_crank.put("payment", payment );
    json_turn_crank.put("action", "place-order");
    client.post(new JsonRepresentation(json_turn_crank), MediaType.APPLICATION_JSON);
  }

  public String toString() {
    String result = "" ;
    Representation result_string = client.get() ; 
    try {
      JSONObject json = new JSONObject( result_string.getText() ) ;
      result = (String) json.get("banner") ;
    }
    catch (Exception e) {
      result = e.getMessage() ;
    }          
    return result ;
  }

}