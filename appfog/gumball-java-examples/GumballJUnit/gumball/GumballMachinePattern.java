package gumball;

/**
 * Write a description of class GumballMachinePattern here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GumballMachinePattern implements IGumball
{
    private NoQuarterState no_qtr ;
    private HasQuarterState has_qtr ;
    private IGumballState current ;
    private boolean hasGumball ;

    public GumballMachinePattern()
    {
        no_qtr = new NoQuarterState(this) ;
        has_qtr = new HasQuarterState(this) ;
        current = no_qtr ;
        hasGumball = false ;
    }

    public void setState( GumballStates state )
    {
        switch (state) {
            case HAS_QUARTER:
                this.current = has_qtr ;
                break ;
            case NO_QUARTER:
                this.current = no_qtr ;
                break ;
            }
    }
        
    public void insertQuarter() throws GumballException {
        current.insertQuarter() ;
    }
    
    public void turnCrank() throws GumballException {
        if (current == has_qtr )
        {
            hasGumball = true ;
        }
        current.turnCrank() ;
    }
    
    public boolean hasGumball() { 
        return this.hasGumball ;
    }
    
    public String takeGumball() { 
        if ( hasGumball )
        {
            hasGumball=false ;
            return "Gumball" ;
        }
        else
        {
            return "No Gumball" ;
        }
    }    
    
}

