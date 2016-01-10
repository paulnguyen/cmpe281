
package debug ;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import org.aspectj.lang.JoinPoint;


aspect Trace  {

    pointcut classes(): within(edu.sjsu.cs.db.sm.Page) ||
			within(edu.sjsu.cs.db.sm.Volume) ||
			within(edu.sjsu.cs.db.sm.Device) ||
			within(edu.sjsu.cs.db.sm.SMImplVersion*.java) ;
    pointcut constructors(): execution(new()) ;
    pointcut methods(): execution(* *(..)) ;


    before(Exception e): handler(Exception) && args(e) {
	System.out.println( "Exception Thrown: " + e.toString() ) ;
	System.out.println( thisJoinPoint.toLongString()) ;
	System.out.println( thisJoinPointStaticPart.getSignature().getDeclaringType().getName() ) ;
    }     


    before(): classes() && constructors() {   
	doTraceEntry(thisJoinPoint, true);
    }
    after(): classes() && constructors() {   
	doTraceExit(thisJoinPoint,  true);
    }
    before(): classes() && methods() {
	doTraceEntry(thisJoinPoint, false);
    }
    after(): classes() && methods() {
	doTraceExit(thisJoinPoint,  false);
    }




    /** NORMAL OBJECT STUFF **/

    public void initStream(PrintStream _stream) {
	setStream(_stream);
    }


    private ThreadLocal stream = new ThreadLocal() {
	    protected Object initialValue() {
		return System.err;
	    }
	};
    private ThreadLocal callDepth = new ThreadLocal() {
	    protected Object initialValue() {
		return new Integer(0);
	    }
	};

    private PrintStream getStream() { 
	return (PrintStream)stream.get(); 
    }
    private void setStream(PrintStream s) { 
	stream.set(s); 
    }
    private int  getCallDepth() { 
	return ((Integer)(callDepth.get())).intValue();
    }
    private void setCallDepth(int n) { 
	callDepth.set(new Integer(n)); 
    }

    private void doTraceEntry (JoinPoint jp, boolean isConstructor) {
	setCallDepth(getCallDepth() + 1);
	printEntering(jp, isConstructor);
    }

    private void doTraceExit (JoinPoint jp,  boolean isConstructor) {
	printExiting(jp, isConstructor);
	setCallDepth(getCallDepth() - 1);
    }

    private void printEntering (JoinPoint jp, boolean isConstructor) {
	printIndent();
	getStream().print("--> ");
	getStream().print(jp);
	printParameters(jp);
	if ( isConstructor )
		getStream().print( " // constructor " ) ;
	getStream().println();

    }

    private void printExiting (JoinPoint jp, boolean isConstructor) {
	printIndent();

	getStream().print("<--  ");
	getStream().print(jp);
	if ( isConstructor )
		getStream().print( " // constructor " ) ;
	getStream().println();

    }


    private void printIndent() {
	for (int i = 0; i < getCallDepth(); i++)
	{
	    getStream().print(" ");
	}
    }

      private void printParameters(JoinPoint jp) {

  	Object[] params = jp.getArgs() ;

  	getStream().print("(");


  	for (int i = 0; i < params.length; i++) {

	    if ( params[i] == null ) {
		getStream().print( "null" ) ;
	    }
	    else {
  	    	getStream().print(params[i].toString());
	    }


  	    if (i < params.length - 1) 
		{
			getStream().print(", ");
		}


  	}
  	getStream().print(")");

      }

}
