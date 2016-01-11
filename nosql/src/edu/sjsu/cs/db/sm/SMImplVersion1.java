package edu.sjsu.cs.db.sm;

import java.util.*;
import java.io.*;

/**
 *  Description of the Class
 *
 *@author     Paul Nguyen
 *@created    March 18, 2003
 */
public class SMImplVersion1 implements SM {
  /**
   *  Constructor for the SMImplVersion1 object
   */
  public SMImplVersion1() { }


  public SM.OID getOID( byte[] oidbytes ) {
	return null ;
  }


  /**
   *  The main program for the SMImplVersion1 class
   *
   *@param  args  The command line arguments
   */
  public static void main(String[] args) {
    System.out.println("Storage Manager - Implementation Version 1.");

    try {

      // create a new SM
      SMImplVersion1 sm = new SMImplVersion1();
      Record found = null;
      edu.sjsu.cs.db.sm.SMImplVersion1.OID rec1_oid = null;
      edu.sjsu.cs.db.sm.SMImplVersion1.OID rec2_oid = null;
      edu.sjsu.cs.db.sm.SMImplVersion1.OID rec3_oid = null;

      // create some records for testing
      Record rec1 = new Record(20);
      Record rec2 = new Record(20);
      Record rec3 = new Record(20);
      rec1.setBytes("This is a test #1".getBytes());
      rec2.setBytes("This is a test #2".getBytes());
      rec3.setBytes("This is a test #3".getBytes());

      // store & fetch
      rec1_oid = (edu.sjsu.cs.db.sm.SMImplVersion1.OID) sm.store(rec1);
      found = sm.fetch(rec1_oid);
      System.out.write(found.getBytes(0, 0));
      System.out.println( "" ) ;
      System.out.println("Fetch Successful!");

      // store & delete
      try {
        rec2_oid = (edu.sjsu.cs.db.sm.SMImplVersion1.OID) sm.store(rec2);
        found = sm.fetch(rec2_oid);
        System.out.write(found.getBytes(0, 0));
        System.out.println( "" ) ;
        System.out.println("Record to be deleted stored!");
        sm.delete(rec2_oid);
        found = sm.fetch(rec2_oid);
        // should throw exception
        System.out.println("Delete Failed!");
      } catch (NotFoundException nfe) {
        System.out.println("Delete successful!");
      }

      // update rec1 with rec3
      try {
        rec3_oid = (edu.sjsu.cs.db.sm.SMImplVersion1.OID) sm.update(rec1_oid, rec3);
        found = sm.fetch(rec3_oid);
        System.out.write(found.getBytes(0, 0));
        System.out.println( "" ) ;
        System.out.println("Update Completed.");
      } catch (NotFoundException nfe) {
        System.out.println("Update of rec1 with rec3 failed!");
      }

      try {
        System.out.println("Trying to fetch rec1...");
        found = sm.fetch(rec1_oid);
        System.out.write(found.getBytes(0, 0));
        System.out.println( "" ) ;
        System.out.println("Failed Update Check. Rec1 Found!");
      } catch (NotFoundException nfe) {
        System.out.println("Update of rec1 with rec3 Successful!");
      }

    } catch (Exception e) {
      System.out.println("Unknown System Failure!");
    }

  }


  /**
   *  Description of the Method
   *
   *@param  rec              Description of Parameter
   *@return                  Description of the Returned Value
   *@exception  IOException  Description of Exception
   */
  public edu.sjsu.cs.db.sm.SM.OID store(Record rec) throws IOException {
    OID oid = new OID(rec.hashCode());
    this.buffer.put(oid.getKey(), rec);
    return oid;
  }


  /**
   *  Description of the Method
   *
   *@param  oid                    Description of Parameter
   *@return                        Description of the Returned Value
   *@exception  NotFoundException  Description of Exception
   *@exception  IOException        Description of Exception
   */
  public Record fetch(edu.sjsu.cs.db.sm.SM.OID oid) throws NotFoundException, IOException {
    Object rec = null;
    rec = this.buffer.get(oid.getKey());
    if (rec == null) {
      throw new NotFoundException();
    } else {
      return (Record) rec;
    }
  }

    public void close () throws SM.IOException
    {

    }

    public void flush () 
    {

    }


  /**
   *  Description of the Method
   *
   *@param  oid                    Description of Parameter
   *@param  rec                    Description of Parameter
   *@return                        Description of the Returned Value
   *@exception  NotFoundException  Description of Exception
   *@exception  IOException        Description of Exception
   */
  public edu.sjsu.cs.db.sm.SM.OID update(edu.sjsu.cs.db.sm.SM.OID oid, Record rec) throws NotFoundException, IOException {
    this.buffer.remove(oid.getKey());
    OID newkey = new OID(rec.hashCode());
    this.buffer.put(newkey.getKey(), rec);
    return newkey;
  }


  /**
   *  Description of the Method
   *
   *@param  oid                        Description of Parameter
   *@exception  NotFoundException      Description of Exception
   *@exception  CannotDeleteException  Description of Exception
   */
  public void delete(edu.sjsu.cs.db.sm.SM.OID oid) throws NotFoundException, CannotDeleteException {
    this.buffer.remove(oid.getKey());
  }


  /**
   *  Description of the Class
   *
   *@author     Paul Nguyen
   *@created    March 18, 2003
   */
  public class OID implements edu.sjsu.cs.db.sm.SM.OID {
    private int key;


    /**
     *  Constructor for the OID object
     *
     *@param  key  Description of Parameter
     */
    public OID(int key) {
      this.key = key;
    }

   public byte[] toBytes()
   {
       return getKey().getBytes();
   }

    /**
     *  Gets the key attribute of the OID object
     *
     *@return    The key value
     */
    public String getKey() {
      return Integer.toString(this.key);
    }
  }


  private Hashtable buffer = new Hashtable();
}

