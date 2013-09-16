
public aspect Hello {
	before() : call( * Main.print() ) {
		System.out.print( "Hello" ) ;
	}
}
