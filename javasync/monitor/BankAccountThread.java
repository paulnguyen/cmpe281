public class BankAccountThread extends Thread
{
    private BankAccount theAcct ;
    private double transactionAmount ;
    
    public BankAccountThread(BankAccount acct, double transAmt)
    {
        this.theAcct = acct ;
        this.transactionAmount = transAmt ;
    }

    public void run()
    {     
        try {
            sleep(1000) ; // sleep for 1 second
            double bal = theAcct.Withdraw( transactionAmount ) ;
            System.out.println( Thread.currentThread().getName() + " DONE! [current balance = "+bal+"]" ) ;
        } catch ( Exception e )
        {
            System.out.println( e.getMessage() ) ;   
        }
    }
}
