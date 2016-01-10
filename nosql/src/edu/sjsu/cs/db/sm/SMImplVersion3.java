package edu.sjsu.cs.db.sm;

import java.util.*;
import java.io.*;
import java.nio.*;

/**
 *  Description of the Class
 *
 *@author     Paul Nguyen
 *@created    March 18, 2003
 */
public class SMImplVersion3 implements SM {
  
  private final static boolean DEBUG = false ;
  
  private Volume master_volume = null ;   // volume id = 0
  private Volume data_volume = null ;     // volume id = 1 
  private Volume index_volume = null ;    // volume id = 2
  private IndexManager m_indexManager;
  

  public SM.OID getOID( byte[] oidbytes ) {
	return null ;
  }
  

  public void flush() {
	  try {
		  close() ;
	  }
	  catch ( Exception e ) {}
  }


  
  /**
   *  Constructor for the SMImplVersion2 object
   *
   *@since
   */
  public SMImplVersion3() {
    
    // mount the bootstrap volume
    master_volume = new Volume(0,"master.db",1,10); // only need two pages, max of 10 pages
    data_volume = new Volume(1,"data.db",20,100);
    index_volume = new Volume(2,"index.db",10,50);

    // create translation table if needed
    Page m_page = master_volume.getPage(0) ;
    ByteBuffer rec_id = ByteBuffer.allocate(8);
    rec_id.putInt(0);
    rec_id.putShort((short) 0);
    rec_id.putShort((short) 1);
    byte[] rec_id_bytes = rec_id.array();    
    ByteBuffer trans_table = m_page.getRecordByteBuffer( rec_id_bytes ) ;
    if ( trans_table == null ) {
      if (DEBUG) {
        System.out.println( "Creating Translation Table..." ) ; 
      }
      byte[] trans_table_array = new byte[ Volume.MAX_TRANSLATION_TABLE_SIZE_PER_PAGE ] ;
      try { 
        byte[] trans_table_recid = m_page.addRecord( trans_table_array ) ;
      } catch (Exception e) {}
      trans_table = m_page.getRecordByteBuffer( rec_id_bytes ) ;
    }
//    this.translation_table = trans_table ;    //CHANGE
      m_indexManager = new IndexManager( trans_table );
    // advance buffer position to end to be ready for new inserts
//    trans_table_reset() ;                     //CHANGE
      m_indexManager.trans_table_reset();

    /* test create a sample trans table entry */
//    if (false) {
////      trans_table_dump() ;                    //CHANGE
//        m_indexManager.trans_table_dump();
//      byte[] guid = new byte[ Volume.RECORD_GUID_SIZE ] ;
//      byte[] recid = new byte[ Volume.RECORD_ID_SIZE ] ;
//      byte b = -1 ;
//      Arrays.fill( guid, b ) ;
//      Arrays.fill( recid, b ) ;
////      this.translation_table.put( guid ) ;    //CHANGE
//        m_indexManager.getTranslationTable().put( guid );
////      this.translation_table.put( recid ) ;   //CHANGE
//        m_indexManager.getTranslationTable().put( recid );
////      trans_table_dump() ;                    //CHANGE
//        m_indexManager.trans_table_dump();
////      byte[] found = trans_table_find_entry( guid ) ; //CHANGE
//        byte[] found = m_indexManager.trans_table_find_entry( guid );
//      System.out.println( "FOUND => " + Util.toHexString(found) ) ;
//      b = 99 ;
//      Arrays.fill( recid, b ) ;
////      trans_table_update_entry( guid, recid ) ;   //CHANGE
//        m_indexManager.trans_table_update_entry( guid, recid );
////      trans_table_dump() ;                        //CHANGE
//        m_indexManager.trans_table_dump();
////      trans_table_delete_entry( guid ) ;          //CHANGE
//        m_indexManager.trans_table_delete_entry( guid );
////      trans_table_dump() ;                        //CHANGE
//        m_indexManager.trans_table_dump();
//    }
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
    Page freepage = null ;
    byte[] recid = null ;
    try {
      freepage = data_volume.getFreePage() ;
      recid = freepage.addRecord( rec.getBytes(0,0) ) ;
    } catch (PageFullException e) {
      if (DEBUG) {
        System.out.println( "Page Full, allocating a new page..." ) ;
      }
      try {
      freepage = data_volume.newFreePage() ;
        recid = freepage.addRecord( rec.getBytes(0,0) ) ;
      } catch (Exception ignore) {}
    }
    byte[] guid = Util.generateGUID() ;
//    trans_table_add_entry( guid, recid ) ;        //CHANGE
      m_indexManager.trans_table_add_entry( guid, recid );
    if (DEBUG)
      System.out.println( "Stored With OID: " + Util.toHexString(guid) ) ;
    return new OID( guid ) ;   
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

    if (DEBUG)
      System.out.println( "Fetch Using OID: " + Util.toHexString(oid.toBytes()) ) ;
    
    Record rec = null ;    
    try {
      
//      byte[] recid = trans_table_find_entry( oid.toBytes() ) ;    //CHANGE
        byte[] recid = m_indexManager.trans_table_find_entry( oid.toBytes() );
      if ( recid == null )
        throw new NotFoundException() ;      

      if (DEBUG)
        System.out.println( "Fetch Found RECID: " + Util.toHexString(recid) ) ;
        
      ByteBuffer recid_buf = ByteBuffer.wrap(recid);
      int rec_vol_id = recid_buf.getInt() ;
      int rec_page_id = (int)recid_buf.getShort();
      Page datapage = data_volume.getPage( rec_page_id ) ;
      byte[] data = datapage.getRecord( recid ) ;
      rec = new Record( data.length ) ;
      rec.setBytes( data ) ;    
      return rec ;
    } catch ( Exception e ) {
      throw new NotFoundException();
    }

  }


  /**
   *  Description of the Method
   *
   *@exception  SM.IOException  Description of Exception
   *@since
   */
  public void close() throws SM.IOException {
    master_volume.close() ;
    data_volume.close() ;
    index_volume.close() ;
      m_indexManager.commit();
      System.out.println( "Close was called..." );
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

      if (DEBUG)
      System.out.println( "Update Using OID: " + Util.toHexString(oid.toBytes()) ) ;

    try {
//      byte[] recid = trans_table_find_entry( oid.toBytes() ) ;    //CHANGE
        byte[] recid = m_indexManager.trans_table_find_entry( oid.toBytes() );
      if ( recid == null )
        throw new NotFoundException() ;      
      ByteBuffer recid_buf = ByteBuffer.wrap(recid);
      int rec_vol_id = recid_buf.getInt() ;
      int rec_page_id = (int)recid_buf.getShort();
      Page datapage = data_volume.getPage( rec_page_id ) ;
      byte[] new_rec_id = datapage.updateRecord( recid, rec.getBytes(0,0) ) ;
//      trans_table_update_entry( oid.toBytes(), new_rec_id ) ;     //CHANGE
        m_indexManager.trans_table_update_entry( oid.toBytes(), new_rec_id );
      return oid ;
    } catch ( Exception e ) {
        if ( DEBUG ) e.printStackTrace();
      throw new NotFoundException();
    }
    
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

    if (DEBUG)
      System.out.println( "Delete Using OID: " + Util.toHexString(oid.toBytes()) ) ;
  
    try {
//      byte[] recid = trans_table_find_entry( oid.toBytes() ) ;        //CHANGE
        byte[] recid = m_indexManager.trans_table_find_entry( oid.toBytes() );
      if ( recid == null )
        throw new NotFoundException() ;            
      ByteBuffer recid_buf = ByteBuffer.wrap(recid);
      int rec_vol_id = recid_buf.getInt() ;
      int rec_page_id = (int)recid_buf.getShort();
      Page datapage = data_volume.getPage( rec_page_id ) ;
      datapage.deleteRecord( recid ) ;
//      trans_table_delete_entry( oid.toBytes() ) ;                     //CHANGE
        m_indexManager.trans_table_delete_entry( oid.toBytes() );
    } catch ( Exception e ) {
        e.printStackTrace();
      throw new NotFoundException();
    }

  }


  /**
   *  Description of the Class
   *
   *@author     Paul Nguyen
   *@created    March 18, 2003
   */
  public class OID implements SM.OID {
    private byte[] key;

    /**
     *  Constructor for the OID object
     *
     *@param  key  Description of Parameter
     *@since
     */
    public OID(byte[] key) {
      this.key = key;
    }


    /**
     *  Gets the key attribute of the OID object
     *
     *@return    The key value
     *@since
     */
    public String getKey() {
      return Util.toHexString(this.key) ;
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
}

