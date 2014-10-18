

public privileged aspect CRANKMODEL {

	// Declarations
	declare parents: GumballMachine implements IGumballMachineSlot;

	// Insert Quarter Introduction
	public void GumballMachine.insertCoin(int value) {
		System.out.println("=> Insert Coin: " + value + " cents");
		// *** insert code here ***
	}

	// Turn Crank Introduction
	public void GumballMachine.returnCoins() {
		System.out.println("=> Return Coins...");
		// *** insert code here ***
	}

	// Test
	after() : execution(static * *.main(..) ) {
		System.out.println("***** Gumball Machine Crank Model 2.0 *****");
		GumballMachine m = new GumballMachine(10);
		m.dumpConfig();
		m.insertCoin(10) ;
		m.insertCoin(5);
		m.returnCoins();  // return 15 cents change
		m.insertCoin(50) ; // ejects gumball
		m.insertCoin(100) ; // ejects gumball + 50 cents change
		m.insertCoin(25) ;
		m.insertCoin(10) ;
		m.insertCoin(5) ;
		m.insertCoin(10) ; // ejects gumball 
		m.dumpConfig() ;
		
	}	
	
}
