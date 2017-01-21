/*
 * Copyright 1999 Hannes Wallnoefer
 */
 
package helma.xmlrpc;

import java.util.Vector;

/**
 * The XML-RPC server uses this interface to call a method of an RPC handler. This should 
 * be implemented by any class that wants to directly take control when it is called over RPC. Classes
 * not implementing this interface will be wrapped into an Invoker 
 * object that tries to find the matching method for an XML-RPC request. 
 */ 

public interface XmlRpcHandler {

    /**
     * Return the result, or throw an Exception if something went wrong. 
     */
    public Object execute (String method, Vector params) throws Exception;

} 