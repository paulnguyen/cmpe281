
public class Main {

	// Main Introductions
	private String theList[] = new String[10] ;
			
	public void print()
	{
		for ( int i = 0; i<10; i++ )
		{
			System.out.println( theList[i] ) ;
		}
	}
	
	public void setup()
	{
		theList[0] = "Item #0" ;
		theList[1] = "Item #1" ;
		theList[2] = "Item #2" ;
		theList[3] = "Item #3" ;
		theList[4] = "Item #4" ;
		theList[5] = "Item #5" ;
		theList[6] = "Item #6" ;
		theList[7] = "Item #7" ;
		theList[8] = "Item #8" ;
		theList[9] = "Item #9" ;		
	}
	
	public static void main(String[] args) 
	{
		Main obj = new Main() ;
		obj.setup();
		obj.print();
	}  	
	
}
