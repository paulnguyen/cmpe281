/** 
 * Copyright 1999 Hannes Wallnoefer
 * Implements an XML-RPC server. See http://www.xmlrpc.com/
 */
 
package helma.xmlrpc;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

/**
 * A multithreaded, reusable XML-RPC server object. The name may be misleading because this does not open any 
 * server sockets. Instead it is fed by passing an XML-RPC input stream to the execute method.
 * If you want to open a HTTP listener, use the WebServer class instead.
 */
public class XmlRpcServer {

    /**
     * 
     */
     
    Hashtable handlers;
   
    /** 
     * Construct a new XML-RPC server. You have to register handlers to make it 
     * do something useful. 
     */
    public XmlRpcServer () {
    	handlers = new Hashtable ();
    }
    	
    /** 
     * Register a handler object with this name. Methods of this objects will be
     * callable over XML-RPC as "handlername.methodname". For more information 
     * about XML-RPC handlers see the <a href="../index.html#1a">main documentation page</a>.
     */
    public void addHandler (String handlername, Object handler) {
	if (handler instanceof XmlRpcHandler || handler instanceof AuthenticatedXmlRpcHandler)
	   handlers.put (handlername, handler);
	else if (handler != null)
	   handlers.put (handlername, new Invoker (handler));
    }

    /**
     * Remove a handler object that was previously registered with this server.
     */
    public void removeHandler (String handlername) {
	handlers.remove (handlername);
    }

    /** 
     * Parse the request and execute the handler method, if one is found. Returns the result as XML. 
     * The calling Java code doesn't need to know whether the call was successful or not since this is all 
     * packed into the response.
     */
    public byte[] execute (InputStream is) {
    	return execute (is, null, null);
    }

    /**
     * Parse the request and execute the handler method, if one is found. If the invoked handler is
     * AuthenticatedXmlRpcHandler, use the credentials to authenticate the user.
     */
    public byte[] execute (InputStream is, String user, String password) {
    	Worker worker = getWorker ();
    	byte[] retval =  worker.execute (is, user, password);
	pool.push (worker);
    	return retval;
    }

    Stack pool = new Stack ();
    int workers = 0;
 
     private final Worker getWorker () {
	try {
	    return (Worker) pool.pop ();
	} catch (EmptyStackException x) {
	    if (workers < 100) {
	        workers += 1;
	        return new Worker ();
	    }
	    throw new RuntimeException ("System overload");
	}
    }
   
 
    class Worker extends XmlRpc {

    Vector inParams;
    Object outParam;
    byte[] result;
    StringBuffer strbuf;

    public byte[] execute (InputStream is, String user, String password) {
	inParams = new Vector ();
	if (strbuf == null)
	    strbuf = new StringBuffer ();
	else
	    strbuf.setLength (0);
	long now = System.currentTimeMillis ();

    	try {
	    parse (is);
	    if (debug) {
    	        System.err.println ("method name: "+methodName);
	        System.err.println ("inparams: "+inParams);
	    }
	    // check for errors from the XML parser
	    if (errorLevel > NONE) 
	        throw new Exception (errorMsg);
	    
	    Object handler = null;
	    
	    String handlerName = null;
	    int dot = methodName.indexOf (".");
	    if (dot > -1) {
	        handlerName = methodName.substring (0, dot);
	        handler = handlers.get (handlerName);
	        if (handler != null)
	            methodName = methodName.substring (dot+1);
	    }

	    if (handler == null) {
	        handler = handlers.get ("$default");
	    }
	    
	    if (handler == null) {
	       if (dot > -1)
	           throw new Exception ("RPC handler object \""+handlerName+"\" not found and no default handler registered.");
	       else
	           throw new Exception ("RPC handler object not found for \""+methodName+"\": no default handler registered.");
	    }
	
	    if (handler instanceof AuthenticatedXmlRpcHandler)
	        outParam = ((AuthenticatedXmlRpcHandler) handler).execute (methodName, inParams, user, password);
	    else
	        outParam = ((XmlRpcHandler) handler).execute (methodName, inParams);
	    if (debug) 
	        System.err.println ("outparam = "+outParam);

	    XmlWriter writer = new XmlWriter (strbuf);
	    writeResponse (outParam, writer);
	    result = writer.getBytes ();

	} catch (Exception x) {
	    if (debug) 
	        x.printStackTrace ();
	    XmlWriter writer = new XmlWriter (strbuf);
	    String message = x.toString ();
	    // check if XmlRpcException was thrown so we can get an error code
	    int code = x instanceof XmlRpcException ? ((XmlRpcException) x).code : 0;
	    writeError (code, message, writer);
	    try {
	        result = writer.getBytes ();
	    } catch (UnsupportedEncodingException encx) {
	        System.err.println ("XmlRpcServer.execute: "+encx);
	        result = writer.toString().getBytes();
	    }
	}
	if (debug)
	    System.err.println ("Spent "+(System.currentTimeMillis () - now)+" millis in request");
	return result;
    }
    
    /**
     * Called when an object to be added to the argument list has been parsed.
     */
    void objectParsed (Object what) {
	inParams.addElement (what);
    }
    
    /**
     * Writes an XML-RPC response to the XML writer.
     */
    void writeResponse (Object param, XmlWriter writer) {
	writer.startElement ("methodResponse");
	// if (param == null) param = ""; // workaround for Frontier bug
	writer.startElement ("params");
	writer.startElement ("param");
	writeObject (param, writer);
	writer.endElement ("param");
	writer.endElement ("params");
	writer.endElement ("methodResponse");
    }
    
     /**
     * Writes an XML-RPC error response to the XML writer.
     */
     void writeError (int code, String message, XmlWriter writer) {
    	// System.err.println ("error: "+message);
	Hashtable h = new Hashtable ();
	h.put ("faultCode", new Integer (code));
	h.put ("faultString", message);
	writer.startElement ("methodResponse");
	writer.startElement ("fault");
	writeObject (h, writer);
	writer.endElement ("fault");
	writer.endElement ("methodResponse");
    }
    
    } // end of inner class Worker

}  // XmlRpcServer

// This class uses Java Reflection to call methods matching an XML-RPC call
class Invoker implements XmlRpcHandler {
	
    private Object invokeTarget;
    private Class targetClass;
		
    public Invoker(Object target) {
	invokeTarget = target;
 	targetClass = invokeTarget instanceof Class ? 
	    (Class) invokeTarget : invokeTarget.getClass();
	if (XmlRpc.debug) 
	    System.err.println("Target object is " + targetClass);
   }


    // main method, sucht methode in object, wenn gefunden dann aufrufen.
    public Object execute (String methodName, Vector params) throws Exception {


	// Array mit Classtype bilden, ObjectAry mit Values bilden	
	Class[] argClasses = null;
	Object[] argValues =  null;
	if(params != null){
	    argClasses = new Class[params.size()];
	    argValues = new Object[params.size()];				
	    for(int i = 0; i < params.size(); i++){ 
	        argValues[i] = params.elementAt(i);
	        if (argValues[i] instanceof Integer)
	            argClasses[i] = Integer.TYPE;
	        else if (argValues[i] instanceof Double)
	            argClasses[i] = Double.TYPE;
	        else if (argValues[i] instanceof Boolean)
	            argClasses[i] = Boolean.TYPE;
	        else 
	            argClasses[i] = argValues[i].getClass();
	    }
	}			
	
	// Methode da ?
	Method method = null;
	
	if (XmlRpc.debug) {
	    System.err.println("Searching for method: " + methodName);
	    for(int i = 0; i < argClasses.length; i++) 
	        System.err.println("Parameter " + i + ": " + argClasses[i] + " = " + argValues[i]);
	}
	
	try {
	    method = targetClass.getMethod(methodName, argClasses);
	}
	// Wenn nicht da dann entsprechende Exception returnen
	catch(NoSuchMethodException nsm_e){			
	    throw nsm_e;
	}
	catch (SecurityException s_e){			
	    throw s_e;
	}
					
	// invoke
	Object returnValue = null;
	try {
	    returnValue = method.invoke (invokeTarget, argValues);
	}
	catch (IllegalAccessException iacc_e){
	    throw iacc_e;
	}
	catch (IllegalArgumentException iarg_e){
	    throw iarg_e;
	}
	catch (InvocationTargetException it_e) {
	    if (XmlRpc.debug)
	        it_e.getTargetException ().printStackTrace ();
	    throw new Exception (it_e.getTargetException ().toString ());
	}
	
	return returnValue;
    }

}
