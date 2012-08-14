/*
 * Copyright 2000 Hannes Wallnoefer
 */
 
package helma.xmlrpc;

import java.util.Vector;

/**
 * An XML-RPC handler that also handles user authentication.
 */ 

public interface AuthenticatedXmlRpcHandler {

    /**
     * Return the result, or throw an Exception if something went wrong.
     */
    public Object execute (String method, Vector params, String user, String password) throws Exception;

} 