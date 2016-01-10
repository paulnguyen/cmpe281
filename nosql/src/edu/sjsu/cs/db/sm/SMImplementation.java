package edu.sjsu.cs.db.sm;

import java.util.*;
import java.io.*;

/**
 *  Description of the Class
 *
 *@author     Paul Nguyen
 *@created    March 18, 2003
 */
public class SMImplementation implements SM {

	private SM sm;


  public SM.OID getOID( byte[] oidbytes ) {
	return null ;
  }


	/**
	 *  Constructor for the MySM object
	 *
	 *@since
	 */
	public SMImplementation() {
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


}

