
public privileged aspect ONEQTR {
	
    // PointCut
	void around(GumballMachine m) : target(m) && execution(void GumballMachine.insertQuarter(..))
	{
		// *** insert code here ***
	}
	
	

}

