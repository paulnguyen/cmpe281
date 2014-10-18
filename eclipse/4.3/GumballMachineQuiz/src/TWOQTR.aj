
public privileged aspect TWOQTR {

    // PointCut
	void around(GumballMachine m) : target(m) && execution(void GumballMachine.insertQuarter(..))
	{
		// *** insert code here ***
	}
	    
			
	
}