

public class Toaster implements KitchenUtility {
    private String modelNumber;
    public String getModelNumber() {
        return 'T' + modelNumber;
	} 
}

/** test code - interfaces
Toaster t = new Toaster();
KitchenUtility [] utilities = new KitchenUtility[] { t };
String model = utilities[0].getModelNumber();
System.debug(model);
**/