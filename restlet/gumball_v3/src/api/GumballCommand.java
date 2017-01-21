
package api ;

public class GumballCommand {

	private String payment ;
	private String action ;
	private String result ;

	public void setAction( String cmd ) { this.action = cmd ; }
	public void setResult( String result ) { this.result = result ; }
	public void setPayment( String payment ) { this.payment = payment ; }

	public String getAction() { return this.action ; }
	public String getResult() { return this.result ; }
	public String getPayment() { return this.payment ; }

}