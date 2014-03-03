trigger HandleOrderUpdate on Invoice_Statement__c (after update) {
    // Create a map of IDs to all of the *old* versions of sObjects 
    // updated by the call that fires the trigger. 
    
    Map<ID, Invoice_Statement__c> oldMap =    new Map<ID, 
    Invoice_Statement__c>(Trigger.old);

    // Create an empty list of IDs 
  
    List<Id> invoiceIds = new List<Id>();

    // Iterate through all of the *new* versions of Invoice_Statement__c 
    // sObjects updated by the call that fires the trigger, adding 
    // corresponding IDs to the invoiceIds list, but *only* when an 
    // sObject's status changed from a non-"Closed" value to "Closed". 
    
    for (Invoice_Statement__c invoice: Trigger.new) {
        if (invoice.status__c == 'Closed' &&  oldMap.get(invoice.Id).status__c != 'Closed'){
            invoiceIds.add(invoice.Id);
        }
    }
    // If the list of IDs is not empty, call Integration.postOrder 
    
    // supplying the list of IDs for fulfillment. 
    
    if (invoiceIds.size() > 0) {
        Integration.postOrder(invoiceIds);
    }
 }