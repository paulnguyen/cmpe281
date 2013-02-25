
		
public class HelloApexWorld
{
	public static void sayYou() { System.debug( 'You') ; }
	public static void sayMe() { System.debug( 'Me') ; }
	public static void sayHello() { System.debug( 'Hello World!') ; }
	
	// data types & variables
	public static void tutorial5()
	{

		// Strings
		String myVariable = 'I am a string.';
		Date myDate = Date.today();
		String myString = String.valueOf(myDate);
		System.debug(myString);		
		String x = 'I am a string';
		String y = 'I AM A STRING';
		String z = 'Hello!';
		System.debug (x == y);
		System.debug (x != z);		
		String x1 = 'The !shorn! sheep !sprang!.';
		System.debug (x1.endsWith('.'));
		System.debug (x1.length());
		System.debug (x1.substring(5,10));
		System.debug (x1.replaceAll ('!(.*?)!', '$1'));
		
		// Conditionals
		Boolean isLeapYear = true;
		if (isLeapYear) {
		    System.debug ('It\'s a leap year!');
		} else {
		    System.debug ('Not a leap year.');
		}
		Boolean isIt = true;
		String x2 = 'You are  ' + (isIt ?  'great' : 'small');
		System.debug(x2);
		
		// Date & Time
		Date myDate2 = Date.newinstance(1960, 2, 17);
		Time myTime2 = Time.newInstance(18, 30, 2, 20);
		System.debug(myDate2);
		System.debug(myTime2);
		Datetime myDateTime = Datetime.now();
		Date today = Date.today();
		Date myToday   = Date.today();
		Date myNext30 = myToday.addDays(30);
		System.debug('myToday =  ' + myToday);
		System.debug('myNext30 =  ' + myNext30);
		
		// Integer and Decimals
		Integer i = 1;
		Long l = 2147483648L;
		Double d = 3.14159;
		Decimal dec = 19.23;
		Integer countMe = Integer.valueof('10') + 20;
		Decimal decBefore = 19.23;
		Decimal decAfter = decBefore.Divide(100, 3);
		System.debug(decAfter);
		
		// Enums.  Unlike Java, the enum type has no constructor syntax.
		Season s = Season.SUMMER;
		if (s == Season.SUMMER) {
		    // Will write the string value SUMMER
		    System.debug(s);
		} else {
		    System.debug('Not summer.');
		}
		
		// IDs - 18-character Force.com record identifier
		ID myId = '00300000003T2PGAA0';
		
		// Blog - useful for encryption algorithms
		DateTime now = System.now();
	    String timestamp = ''+ now.year() + '-' +
	    now.month() + '-' +
	    now.day() + '\'T\'' +
	    now.hour() + ':' +
	    now.minute() + ':' +
	    now.second() + '.' +
	    now.millisecond() + '\'Z\'';
	    String timeFormat = now.formatGmt(timestamp);
	    String urlEncodedTimestamp = EncodingUtil.urlEncode(timestamp, 'UTF-8');
	    String action = 'UrlInfo';
	    String inputStr = action + timeFormat;
	    String algorithmName = 'HMacSHA1';
	    Blob mac = Crypto.generateMac(algorithmName,  Blob.valueOf(inputStr), 
	                                                    Blob.valueOf('your_signing_key'));
	    String macUrl = EncodingUtil.urlEncode(EncodingUtil.base64Encode(mac), 'UTF-8');
		System.debug( mac.toString() ) ;
				
	}
	
	// Case Sensitivity, Collections, Sets, Maps, Loops
	public static void tutorial6()
	{
		// Unlike Java, case is insensitive
		sayYou();
		sayyou();
		SayYou();
		System.debug ( System.today() );
		System.debug ( System.Today() );
		System.debug ( SySteM.Today() );
		
		// Arrays and Lists
		List<Integer> myList1 = new List<Integer>();
		System.debug(myList1.size());
		Integer[ ] myList2 = new List<Integer>();
		System.debug(myList2.size());
		List<String> myStrings = new List<String> { 'one', 'two' };
		Integer[] myList = new List<Integer>();
		//Adds a new element with value 10 to the end of the list
		myList.add(10);
		// Retrieve the first element of the list
		// using array notation
		Integer i = myList[0];
		// or using the get method
		Integer j = myList.get(0);
		System.debug('First element in the array using myList[0] is ' + i);
		System.debug('First element in the array using myList.get(0) is ' + j);
		
		Integer[ ] myList3 = new List<Integer>{10, 20};
		// Modify the value of the first element
		// using the array notation
		myList3[0] = 15;
		// or using the set method
		myList3.set(0,15);
		System.debug ('Updated value:' + myList3[0]);
		// Return the size of the list
		System.debug ('List size: ' + myList3.size());
		
		// While Loops
		Integer count = 1;
		do {
		    System.debug(count);
		    count++;
		} while (count < 11);
		count = 1;
		do {
		    System.debug(count);
		    count++;
		} while (count < 11);
		
		// For Loops
		for (Integer z = 1; z <= 10; z++)
		{
		    System.debug(z);
		}
		Integer[ ] myIntegers = new Integer[ ]{10,20,30,40,50,60,70,80,90,100};
		for (Integer k: myIntegers) {
		 System.debug(k);
		}
		
		// Sets
		Set<String> s = new Set<String>{'a','b','c'};
		// Because c is already a member, nothing will happen.
		s.add('c');
		s.add('d');
		if (s.contains('b')) {
		    System.debug ('I contain b and have size ' + s.size());
		}
		
		// Maps
		Map<Integer,String> employeeAddresses = new Map<Integer,String>();
		employeeAddresses.put (1, '123 Sunny Drive, San Francisco, CA');
		employeeAddresses.put (2, '456 Dark Drive, San Francisco, CA');
		System.debug('Address for employeeID 2: ' + employeeAddresses.get(2));
		Map<String,String> myMapStrings =
		new Map<String,String>{'a'=>'apple','b'=>'bee'};
		System.debug(myMapStrings.get('a'));
		
	}
	
	
}