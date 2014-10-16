
public privileged aspect GumballOneQuarter {

    // PointCut
    before(GumballMachine m): target(m) && call(void GumballMachine.insertQuarter(..)) {
    	int val = m.getCoinValue() ;
    	if ( val >= 25 ) {
    		System.out.println( "Machine only accepts one Quarter!" ) ;
    	}
    	return ;
    }
	
	
}

