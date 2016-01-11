package edu.sjsu.cs.db.sm;

import java.util.*;
import java.io.*;

/**
 *  Description of the Class
 *
 *@author     Paul Nguyen
 *@created    March 18, 2003
 */
public class SMImplVersion0 implements SM {

	private SM sm;


  public SM.OID getOID( byte[] oidbytes ) {
	return null ;
  }


	/**
	 *  Constructor for the MySM object
	 *
	 *@since
	 */
	public SMImplVersion0() {
		this.sm = SMFactory.getInstance();
	}



	/**
	 *  Description of the Method
	 *
	 *@param  rec              Description of Parameter
	 *@return                  Description of the Returned Value
	 *@exception  IOException  Description of Exception
	 *@since
	 */
	public SM.OID store(Record rec) throws IOException {
		return sm.store(rec);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  oid                    Description of Parameter
	 *@return                        Description of the Returned Value
	 *@exception  NotFoundException  Description of Exception
	 *@exception  IOException        Description of Exception
	 *@since
	 */
	public Record fetch(SM.OID oid) throws NotFoundException, IOException {
		return sm.fetch(oid);
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  SM.IOException  Description of Exception
	 *@since
	 */
	public void close() throws SM.IOException {
		sm.close();
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  SM.IOException  Description of Exception
	 *@since
	 */
	public void flush()  {
		try { sm.flush(); } catch (Exception e) {}
	}



	/**
	 *  Description of the Method
	 *
	 *@param  oid                    Description of Parameter
	 *@param  rec                    Description of Parameter
	 *@return                        Description of the Returned Value
	 *@exception  NotFoundException  Description of Exception
	 *@exception  IOException        Description of Exception
	 *@since
	 */
	public SM.OID update(SM.OID oid, Record rec) throws NotFoundException, IOException {
		return sm.update(oid, rec);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  oid                        Description of Parameter
	 *@exception  NotFoundException      Description of Exception
	 *@exception  CannotDeleteException  Description of Exception
	 *@since
	 */
	public void delete(SM.OID oid) throws NotFoundException, CannotDeleteException {
		sm.delete(oid);
	}


 public static void main(String[] args) {
    System.out.println("Storage Manager - Implementation Version 0.");

    try {

      // create a new SM
      SMImplVersion0 sm = new SMImplVersion0();
      Record found = null;
      edu.sjsu.cs.db.sm.SMImplVersion0.OID rec1_oid = null;
      edu.sjsu.cs.db.sm.SMImplVersion0.OID rec2_oid = null;
      edu.sjsu.cs.db.sm.SMImplVersion0.OID rec3_oid = null;

      // create some records for testing
      Record rec1 = new Record(20);
      Record rec2 = new Record(20);
      Record rec3 = new Record(20);
      rec1.setBytes("This is a test #1".getBytes());
      rec2.setBytes("This is a test #2".getBytes());
      rec3.setBytes("This is a test #3".getBytes());

      // store & fetch
      rec1_oid = (edu.sjsu.cs.db.sm.SMImplVersion0.OID) sm.store(rec1);
      found = sm.fetch(rec1_oid);
      System.out.write(found.getBytes(0, 0));
      System.out.println( "" ) ;
      System.out.println("Fetch Successful!");


    } catch (Exception e) {
      System.out.println("Unknown System Failure!");
    }

  }



}

