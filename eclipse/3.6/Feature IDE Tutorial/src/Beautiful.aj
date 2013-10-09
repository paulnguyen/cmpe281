
public aspect Beautiful {
	after() : call( * Main.print() ) {
		System.out.print( " Beautiful" ) ;
	}
}
