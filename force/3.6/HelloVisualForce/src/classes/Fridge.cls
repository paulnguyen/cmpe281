public class Fridge {

    //public String modelNumber;
    //public Integer numberInStock;

	private String modelNumber; 
	private Integer numberInStock;

	public static final Integer stockThreshold = 5;

	public Fridge() {
	    modelNumber = 'XX-XX';
	    numberInStock = 0;
	}
	
	public Fridge(String theModelNumber, Integer theNumberInStock) {
	    modelNumber = theModelNumber;
	    numberInStock = theNumberInStock;
	}
	
    public void updateStock(Integer justSold) 
	{
    	numberInStock = numberInStock - justSold;
	} 
	
	public void setModelNumber(String theModelNumber) {
	    modelNumber = theModelNumber;
	}
	
	public String getModelNumber() {
	    return modelNumber;
	}	
	
	public static void toDebug(Fridge aFridge) {
	    System.debug ('ModelNumber = ' + aFridge.modelNumber);
	    System.debug ('Number in Stock = ' + aFridge.numberInStock);
	}	
	
	public Integer ecoRating {
	   get { return ecoRating; }
	   set { ecoRating = value; if (ecoRating < 0) ecoRating =0; }
	}	
}

/** tester code - public access 
Fridge myFridge = new Fridge();
myFridge.modelNumber = 'MX-O';
myFridge.numberInStock = 100;
myFridge.updateStock(20);
Fridge myOtherFridge = new Fridge();
myOtherFridge.modelNumber = 'MX-Y';
myOtherFridge.numberInStock = 50;
System.debug('myFridge.numberInStock=' + myFridge.numberInStock);
System.debug('myOtherFridge.numberInStock=' + myOtherFridge.numberInStock);
**/

/** test code - private access
Fridge myFridge = new Fridge();
myFridge.setModelNumber('MX-EO');
System.debug(myFridge.getModelNumber());
**/

/** test code - static variable
Fridge myFridge = new Fridge('MX-Y', 200);
Fridge.toDebug(myFridge);
**/

/** test code - property
Fridge myFridge = new Fridge('E', 10);
myFridge.ecoRating = -5;  // calls the setter
System.debug (myFridge.ecoRating); // calls the getter
**/