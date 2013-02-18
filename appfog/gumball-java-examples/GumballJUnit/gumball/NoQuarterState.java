package gumball;


public class NoQuarterState implements IGumballState
{

    private GumballMachinePattern gumball_machine ;

    public NoQuarterState( GumballMachinePattern m )
    {
        this.gumball_machine = m ;
    }


    public void insertQuarter() throws GumballException {
        gumball_machine.setState( GumballStates.HAS_QUARTER ) ;
    }
    
    
    public void turnCrank() throws GumballException {
        throw new NoQuarterException() ;
    }    
    
}
