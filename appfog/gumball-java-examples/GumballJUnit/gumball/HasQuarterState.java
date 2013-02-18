package gumball;


public class HasQuarterState implements IGumballState
{
    private GumballMachinePattern gumball_machine ;

    public HasQuarterState( GumballMachinePattern m )
    {
        this.gumball_machine = m ;
    }

    public void insertQuarter() throws GumballException {
        throw new HasQuarterException() ;
    }
    
    public void turnCrank() throws GumballException {
        gumball_machine.setState( GumballStates.NO_QUARTER ) ;
    }   
}
