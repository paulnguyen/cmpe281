/*

• The class is defined with the @isTest annotation. You saw this annotation in the previous lesson 
  to define a common test utility class. In this lesson, this annotation is used to mark the class 
  as a test class to contain test methods that Apex can execute. Note that any Apex class can contain 
  test methods mixed with other code when not defined with this annotation, but it is recommended to use it.

• The class contains three test methods. Test methods are static, top-level methods that take no arguments. 
  They’re defined with the testmethod keyword or the @isTest annotation. Both of these forms are valid 
  declarations:

• Declaration of a test method using the testmethod keyword.

	static testmethod void myTest() {
    	// Add test logic
	}

• Declaration of a test method using the @isTest annottion.

￼	static @isTest void myTest() {
    	// Add test logic
	}

*/


@isTest
private class TestInvoiceStatementDeletion
{

	static testmethod void TestDeleteInvoiceWithLineItem() {
		
	        // Create an invoice statement with a line item then try to delete it
	        Invoice_Statement__c inv = TestDataFactory.createOneInvoiceStatement(true);
	        Test.startTest();
	        Database.DeleteResult result = Database.delete(inv, false);
	        Test.stopTest();
	        // Verify invoice with a line item didn't get deleted.
	        System.assert(!result.isSuccess());
	    }
	
	static testmethod void TestDeleteInvoiceWithoutLineItems() {
		        // Create an invoice statement without a line item and try to delete it
		        Invoice_Statement__c inv = TestDataFactory.createOneInvoiceStatement(false);
		        Test.startTest();
		        Database.DeleteResult result = Database.delete(inv, false);
		        Test.stopTest();
		        // Verify invoice without line items got deleted.
		        System.assert(result.isSuccess());
	}

	static testmethod void TestBulkDeleteInvoices() {
	        // Create two invoice statements, one with and one with out line items
	        // Then try to delete them both in a bulk operation, as might happen
	        // when a trigger fires.
	        List<Invoice_Statement__c> invList = new List<Invoice_Statement__c>();
	        invList.add(TestDataFactory.createOneInvoiceStatement(true));
	        invList.add(TestDataFactory.createOneInvoiceStatement(false));
	        Test.startTest();
	        Database.DeleteResult[] results = Database.delete(invList, false);
	        Test.stopTest();
	        // Verify the invoice with the line item didn't get deleted
	        System.assert(!results[0].isSuccess());
	        // Verity the invoice without line items did get deleted.
	        System.assert(results[1].isSuccess());
	    }	
	
}