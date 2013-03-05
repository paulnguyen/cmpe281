
/*
	What is an sObject? --> not objects of Apex classes, but peristence data objects -- i.e. domain objects
*/

sObject s = new Invoice_Statement__c() ;  // can reference any persistant object
Invoice_Statement__c inv = 
	new Invoice_Statement__c(Description__c='Test Invoice', Status__c='Pending');
ID id = inv.Id;
String x = inv.Description__c;
System.debug( id ) ;
System.debug( x ) ;

/*
  	The same way database systems support a query language for data retrieval, the Force.com peristence 
	layer also provides two query languages.
	
	• 	Salesforce Object Query Language (SOQL) is a query-only language. While similar to SQL in some ways, 
		it's an object query language that uses relationships, not joins, for a more intuitive navigation of 
		data. This is the main query language that is used for data retrieval of a single sOobject and its 
		related sObjects. You'll see an example in a minute.
		
	• 	Salesforce Object Search Language (SOSL) is a simple language for searching across all multiple 
		persisted objects simultaneously. SOSL is similar to Apache Lucene.
		
	You can write queries directly in Apex without much additional code since Apex is tightly integrated 
	with the database.
*/

// SOQL Example.  Try out in Force.com Explorer
SELECT Description__c, Name, Price__c, Id, Total_Inventory__c FROM Merchandise__c

// sObject type storing query result
sObject s = [SELECT Id, Name FROM Merchandise__c WHERE Name='iPhone 5'];
System.debug( s.id );

// A collection os sObjects
String myName = 'iPhone 5';
Merchandise__c[ ] ms = [SELECT Id FROM Merchandise__c WHERE Name=:myName];
for (sObject o: ms) {
 System.debug(o);
}

// Accessing sObject from Query Result
Double totalInventory = [SELECT Total_Inventory__c
                         FROM Merchandise__c
                         WHERE Name = 'iPhone 5'][0].Total_Inventory__c;
System.debug('Total inventory: ' + totalInventory);


// SOSL Example:
// SOSL statements evaluate to a list of lists of sObjects, where each list contains the 
// search results for a particular sObject type. Here's an example that searches all 
// field across all Merchandise__c and Inventory_Statement__c sObjects. Execute the following:

List<List<SObject>> searchList = [FIND 'Ke*' IN ALL FIELDS RETURNING
                                  Merchandise__c (Id, Name), Invoice_Statement__c];
Merchandise__c[] merList = ((List<Merchandise__c>)searchList[0]);
Invoice_Statement__c[] invList = ((List<Invoice_Statement__c>)searchList[1]);
System.debug('Found ' + merList.size() + ' merchandise items.');
System.debug('Found ' + invList.size() + ' invoice statements.');

// Querying sObject Relationships.  If an sObject is related to another by a master-detail 
// or lookup relationship, you can query the parent sObject field by specifying the relationship 
// name and field name in your SELECT statement as follows:

// Traverses a relationship using the dot notation. (navigating to parent object)
Line_Item__c li = [SELECT Invoice_Statement__r.Name FROM Line_Item__c LIMIT 1];
System.debug('Invoice statement name: ' + li.Invoice_Statement__r.Name);  

// Access child records.
Invoice_Statement__c inv = 
  [ SELECT Id, Name, (SELECT Units_Sold__c FROM Line_Items__r)
    FROM Invoice_Statement__c
    WHERE Name='INV-0001'
  ] ;
List<Line_Item__c> lis = inv.Line_Items__r;
System.debug( 'Number of child line items: ' + lis.size() );

// SOQL in For Loops
for (Merchandise__c tmp : [SELECT Id FROM Merchandise__c]) {
   System.debug( tmp ) ;
}
for (Merchandise__c[] tmp : [SELECT Id FROM Merchandise__c]) {
	for (sObject o: tmp) {
	 System.debug(o);
	}
}

// Apex DML.  sObject ID is populated after insert.
Invoice_Statement__c inv = new Invoice_Statement__c(Description__c='My New Invoice2'); 
System.debug(inv.id) ;
insert inv;
System.debug(inv.id) ;

// Update
Invoice_Statement__c inv = [SELECT Status__c
                            FROM Invoice_Statement__c
                            WHERE Description__c='My New Invoice'];
inv.Status__c = 'Negotiating'; 
update inv;

// Delete
Invoice_Statement__c inv = [SELECT Status__c
							FROM Invoice_Statement__c
							WHERE Description__c='My New Invoice'];
delete inv;

// Undelete (single item)
Invoice_Statement__c inv = [SELECT Status__c
                            FROM Invoice_Statement__c
WHERE Description__c='My New Invoice' ALL ROWS];
undelete inv;

// Undelete (collection of items)
Invoice_Statement__c inv = [SELECT Status__c
                            FROM Invoice_Statement__c
WHERE Description__c='My New Invoice' ALL ROWS];
undelete inv;


// Exceptions
try {
    Merchandise__c m = new Merchandise__c();
    insert m;
} catch (DmlException e) {
    System.debug('The following exception has occurred: ' + e.getMessage());
}

try {
    Merchandise__c m = [SELECT Name FROM Merchandise__c LIMIT 1];
    // Causes an SObjectException because we didn't retrieve
    // the Total_Inventory__c field.
    Double inventory = m.Total_Inventory__c;
} catch(SObjectException se) {
    System.debug('The following exception has occurred: ' + se.getMessage());
}

try {
    Merchandise__c m = [SELECT Name FROM Merchandise__c LIMIT 1];
    // Causes an SObjectException because we didn't retrieve
    // the Total_Inventory__c field.
    Double inventory = m.Total_Inventory__c;
} catch(Exception e) {
	   System.debug('Exception type caught: ' + e.getTypeName());
	    System.debug('Message: ' + e.getMessage());
	    System.debug('Cause: ' + e.getCause());    // returns null
	    System.debug('Line number: ' + e.getLineNumber());
	    System.debug('Stack trace: ' + e.getStackTraceString());
}	
	
