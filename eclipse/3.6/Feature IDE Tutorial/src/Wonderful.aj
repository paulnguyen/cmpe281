
public aspect Wonderful {
	after() : call( * Main.print() ) {
		System.out.print( " Wonderful" ) ;
	}
}
