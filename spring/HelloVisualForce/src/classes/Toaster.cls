public class Toaster implements KitchenUtility {
    private String modelNumber;
    public String getModelNumber() {
        return 'T' + modelNumber;
	} 
}

/** test code - interfaces
Fridge f = new Fridge('MX', 200);
Toaster t = new Toaster();
KitchenUtility [] utilities = new KitchenUtility[] { f, t };
String model = utilities[0].getModelNumber();
System.debug(model);
**/