
public privileged aspect COST25 {

	// Initialize Cost of Gumball 
	after() returning(GumballMachine m) : call(GumballMachine.new(..)) {
		// *** insert code here ***
	}	
	
}