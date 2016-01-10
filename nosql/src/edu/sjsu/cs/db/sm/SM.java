package edu.sjsu.cs.db.sm;

import java.util.Arrays;

/**
 *@author     Paul Nguyen
 *@created    May 3, 2003
 *@version    1.0 The main interface to the SM. The storage manager (SM) is the
 *      primary interface to all database I/O. SM implementers will provide the
 *      ability to store and fetch arbitrarily size records. Each record will be
 *      indentified by a (hopefully) unique OID. Implementers may choose to
 *      implement one or more OID types and whether or not these OIDs can be
 *      reused.
 */
public interface SM {

  /**
   *  Construct a SM. SM implementers will need to provide a constructor that
   *  creates an SM intstance while prviding for constructor arguments that
   *  connect the SM to appropriate disk volumes or one or more files contianing
   *  persistent data. XXX (may consider developping a storage manager Factory
   *  class for instantiating new SM instances and deal with default
   *  initialization)
   *
   *@param  rec              Description of Parameter
   *@return                  Description of the Returned Value
   *@exception  IOException  Description of Exception
   *@since
   */
  //SM(...);

  /**
   *  Construct a SM. SM implementers will need to provide a constructor that
   *  creates an SM intstance while prviding for constructor arguments that
   *  connect the SM to appropriate disk volumes or one or more files contianing
   *  persistent data. XXX (may consider developping a storage manager Factory
   *  class for instantiating new SM instances and deal with default
   *  initialization)
   *
   *  Store the given record and return its OID.
   *
   *@param  rec              Description of Parameter
   *@return                  Description of the Returned Value
   *@return                  an OID.
   *@exception  IOException  Description of Exception
   *@since
   */
  OID store(Record rec) throws IOException;


  /**
   *  Fetch a record by OID. Return a reference to a record thus instantiating
   *  an in-memory Record instance whose address is given by the specified OID.
   *
   *@param  oid                    an OID.
   *@return                        a Record.
   *@exception  NotFoundException  when there is no record for the specified
   *      OID.
   *@exception  IOException        when there is an IO error in the SM.
   *@since
   */
  Record fetch(OID oid) throws NotFoundException, IOException;


  /**
   *  Update by replacing the Record at the specified OID with the new Record
   *  specified.
   *
   *@param  oid                    an OID.
   *@param  rec                    a Record.
   *@return                        Description of the Returned Value
   *@exception  NotFoundException  when there is no record for the specified
   *      OID.
   *@exception  IOException        when there is an IO error in the SM.
   *@since
   */
  OID update(OID oid, Record rec) throws NotFoundException, IOException;


  /**
   *  Delete the stored record whose address is given by the specified OID.
   *  Implementers may also chose to remove any in memory, cached copies if that
   *  would be appropriate. Implementers may choose whether or not to reuse the
   *  OID for subsequently created records.
   *
   *@param  oid                        an OID.
   *@exception  NotFoundException      when there is no record for the given
   *      OID.
   *@exception  CannotDeleteException  when some other problem occurs.
   *@since
   */
  void delete(OID oid) throws NotFoundException, CannotDeleteException;


  /**
   *  Description of the Method
   *
   *@exception  IOException  Description of Exception
   *@since
   */
  void close() throws IOException;

  void flush() ;

  OID getOID( byte[] bytes ) ;

  /**
   *  This is a tagging interface used to abstract a concrete OID
   *  implementataion. SM implementers extend this interface thus providing a
   *  concrete implementatation for an OID. Note that OID implementations must
   *  provide a constuctor that instantiates an OID from a byte array. XXX (may
   *  consider developping an OIDFactory to ensure uniform semantics for
   *  instantiation)
   *
   *@author     Paul Nguyen
   *@created    May 3, 2003
   */
  public static interface OID {
    /**
     *  Gets the key attribute of the OID object
     *
     *@return    The key value
     *@since
     */
    String getKey();


    /**
     *  Description of the Method
     *
     *@return    Description of the Returned Value
     *@since
     */
    byte[] toBytes();
  }


  /**
   *  Record implements a wrapper around an arbirtrary length array of bytes.
   *  XXX (may be better as an abstract class or interface)
   *
   *@author     Paul Nguyen
   *@created    May 3, 2003
   */
  public static class Record {
    /**
     *  Description of the Field
     *
     *@since
     */
    protected byte[] data;

    /**
     *  Construct a Record with the specified number of bytes.
     *
     *@param  size             a int.
     *@exception  SMException  Description of Exception
     *@since
     */
    Record(int size) throws SMException {
      try {
        data = new byte[size];
      } catch (java.lang.OutOfMemoryError oome) {
        throw new SMException();
      }
    }


    /**
     *  Description of the Method
     *
     *@param  o  Description of Parameter
     *@return    Description of the Returned Value
     *@since
     */
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Record)) {
        return false;
      }

      final Record l_fileRecord = (Record) o;

      if (!Arrays.equals(data, l_fileRecord.data)) {
        return false;
      }

      return true;
    }

    //    void truncate(long length);

    /**
     *  Copies the given array to the record, possibly overwriting any
     *  previously copied data and returns the number of bytes successfully
     *  copied.
     *
     *@param  someData  The new bytes value
     *@return           a int.
     *@since
     */
    public int setBytes(byte[] someData) {
      if (someData == null) {
        return 0;
      } else {
        int minLength = (data.length < someData.length)
             ? data.length
             : someData.length;
        for (int i = 0; i < minLength; i++) {
          data[i] = someData[i];
        }
        return someData.length;
      }
    }


    /**
     *  getBytes.
     *
     *@param  pos     a long.
     *@param  length  a int.
     *@return         a byte[].
     *@since
     */
    public byte[] getBytes(long pos, int length) {
      return data;
    }

    public byte[] getBytes(long pos, long length) {
	    return data ;
    }

    /**
     *  Returns the length of the Record.
     *
     *@return    a long.
     *@since
     */
    public long length() {
      return data.length;
    }
  }


  /**
   *  This exception is used to signal any of a number of possible IO, OID,
   *  memory, or other internal SM problems. As the root of all SM exceptions
   *  its type can used to catch all SM exceptions.
   *
   *@author     Paul Nguyen
   *@created    May 3, 2003
   */
  public static class SMException extends java.lang.Exception {
  }


  /**
   *  This exception is used to signal lookup failures.
   *
   *@author     Paul Nguyen
   *@created    May 3, 2003
   */
  public static class NotFoundException extends SMException {
  }


  /**
   *  This exception is used to signal IO failures.
   *
   *@author     Paul Nguyen
   *@created    May 3, 2003
   */
  public static class IOException extends SMException {
  }


  /**
   *  This exception is used to signal deletion problems.
   *
   *@author     Paul Nguyen
   *@created    May 3, 2003
   */
  public static class CannotDeleteException extends SMException {
  }

  // XXX (any other exceptions can be added here)
}


