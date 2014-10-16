
public privileged aspect GumballTwoQuarters {


    // PointCut
    before(GumballMachine m): target(m) && call(void GumballMachine.insertQuarter(..)) {
    	int val = m.getCoinValue() ;
    	if ( val >= 50 ) {
    		System.out.println( "Machine accepts two Quarters!" ) ;
    	}
    	return ;
    }
		
	
}
