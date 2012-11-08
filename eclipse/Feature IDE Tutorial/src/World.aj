
public aspect World {
	after() : call( * Main.print() ) {
		System.out.print( " World" ) ;
	}
}
