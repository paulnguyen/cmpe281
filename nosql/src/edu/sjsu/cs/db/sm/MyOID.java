package edu.sjsu.cs.db.sm;

import java.util.*;
import java.io.*;
import java.nio.*; 

public class MyOID implements SM.OID {
    private byte[] key;
    private int intKey;
    private int keyType = 0;

    /**
     *  Constructor for the OID object
     *
     *@param  key  Description of Parameter
     *@since
     */
    public MyOID(byte[] key) {
      this.key = key;
	 this.keyType = 0 ;
    }

    public MyOID(int key) {
      this.intKey = key;
	 this.keyType = 1 ;
    }


    /**
     *  Gets the key attribute of the OID object
     *
     *@return    The key value
     *@since
     */
    public String getKey() {
	    if ( this.keyType == 1 ) {
		    return Integer.toString(this.intKey);
	    }
	    else {
		    return Util.toHexString(this.key) ;
	    }
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Returned Value
     *@since
     */
    public byte[] toBytes() {
      return this.key ;
    }
  }
