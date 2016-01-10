
package edu.sjsu.cs.db.sm;

import java.util.*;
import java.nio.*;
import java.nio.channels.*;


/**
 *  Description of the Class
 *
 *@author     Paul Nguyen
 *@created    April 24, 2003
 */
public class Volume {

  /**
   *  Description of the Field
   *
   *@since
   */
  public final static int VOL_ID_SIZE = 4;// 4 bytes
  /**
   *  Description of the Field
   *
   *@since
   */
  public final static int PAGE_ID_SIZE = 2;// 2 bytes
  /**
   *  Description of the Field
   *
   *@since
   */
  public final static int BLOCK_ID_SIZE = 2;// 2 bytes
  /**
   *  Description of the Field
   *
   *@since
   */
  public final static int RECORD_ID_SIZE = 8;// 8 bytes. (volid+pageid+blocknum)
  /**
   *  Description of the Field
   *
   *@since
   */
  public final static int RECORD_GUID_SIZE = 32;// 32 bytes guid (logical record ids)
  /**
   *  Description of the Field
   *
   *@since
   */
  public final static int TRANSLATION_TABLE_ENTRIES = 100000;// 100K Entries @ 40 bytes each
  /**
   *  Description of the Field
   *
   *@since
   */
  public final static int MAX_TRANSLATION_TABLE_SIZE_PER_PAGE = TRANSLATION_TABLE_ENTRIES * 40;// 4 bytes - free page #
  private final static int DEFAULT_EXTENT_SIZE = 10;// reserve 10 pages
  private final static int DEFAULT_MAX_EXTENTS = 20;// max volume size
  private final static boolean DEBUG = true;// 100K Entries @ 40 bytes each

  private int vol_hdr_size;// 4 bytes - # of bytes for header
  private int vol_id;// 4 bytes - physical id of volume
  private int vol_page_size;// 4 bytes - # of bytes in a page
  private int vol_block_size;// 4 bytes - # of bytes in a block
  private int vol_num_pages;// 4 bytes - # of pages allocated in volume
  private int vol_extent_size;// 4 bytes - # of pages reserved in volume
  private int vol_max_extents;// 4 bytes - max # of pages in volume
  private int vol_free_page; // 4 bytes - current free page counter


  public int getVolumeId() { return this.vol_id ; }

  /**
   *@since
   *@supplierCardinality    1
   */
  private Device vol_device;

  /**
   *@since
   *@link     dependency
   */
  /*
   *  #Page lnkPage;
   */
  private HashMap vol_pages;


  /**
   *  Constructor for the Volume object
   *
   *@param  filename  Description of Parameter
   *@param  id        Description of Parameter
   *@since
   */
  public Volume(int id, String filename) {
    this(id, filename, DEFAULT_EXTENT_SIZE, DEFAULT_MAX_EXTENTS);
  }


  /**
   *  Constructor for the Volume object
   *
   *@param  id        Description of Parameter
   *@param  filename  Description of Parameter
   *@param  reserve   Description of Parameter
   *@param  max       Description of Parameter
   *@since
   */
  public Volume(int id, String filename, int reserve, int max) {
    vol_device = new Device(filename);
    try {
      if (vol_device.mount()) {
        // New Volume.  Create Header Block.
        if (DEBUG) {
          System.out.println("Creating Header Block...");
        }
        vol_hdr_size = Device.BLOCK_SIZE;// one block
        vol_id = id;
        vol_page_size = Device.PAGE_SIZE;// 512 blocks per page
        vol_block_size = Device.BLOCK_SIZE;// 8K blocks
        vol_num_pages = 0;// start with no pages
        vol_extent_size = reserve;// reserve pages in volume
        vol_max_extents = max;// max growth limit
        vol_free_page = 0;// page 0 free to start
        reservePages(vol_extent_size);
        allocateNewPage();// allocate first page (page allocation includes implicit vol hdr flush)
        if (DEBUG) {
          printVolumeHeader();
        }
      } else {
        // Read In Header Block
        ByteBuffer buffer = vol_device.getBlock(0);
        this.vol_hdr_size = buffer.getInt();
        this.vol_id = buffer.getInt();
        this.vol_page_size = buffer.getInt();
        this.vol_block_size = buffer.getInt();
        this.vol_num_pages = buffer.getInt();
        this.vol_extent_size = buffer.getInt();
        this.vol_max_extents = buffer.getInt();
        this.vol_free_page = buffer.getInt() ;
        if (DEBUG) {
          printVolumeHeader();
          Page page0 = getPage(0);
          page0.dumpHeader();
        }
      }
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  args  Description of Parameter
   *@since
   */
  public static void main(String args[]) {

    Volume m_volume = new Volume(0, "master.db", 2, 10);
    Page m_page = m_volume.getPage(0);
    ByteBuffer rec_id = ByteBuffer.allocate(8);
    rec_id.putInt(0);
    rec_id.putShort((short) 0);
    rec_id.putShort((short) 1);
    byte[] rec_id_bytes = rec_id.array();
    ByteBuffer trans_table = m_page.getRecordByteBuffer(rec_id_bytes);
    if (trans_table == null) {
      System.out.println("Creating Translation Table...");
      byte[] trans_table_array = new byte[MAX_TRANSLATION_TABLE_SIZE_PER_PAGE];
      try {
        m_page.addRecord(trans_table_array);
      } catch (Exception e) {}
    }

    /*
     *  ITERATOR TESTING
     *  Volume master_volume = new Volume(0,"master.db",2,10);
     *  Page m_page = master_volume.getPage(0) ;
     *  m_page.addRecord( "THIS IS A TEST #1".getBytes() ) ;
     *  m_page.addRecord( "THIS IS A TEST #2".getBytes() ) ;
     *  m_page.addRecord( "THIS IS A TEST #3".getBytes() ) ;
     *  m_page.addRecord( "THIS IS A TEST #4".getBytes() ) ;
     *  m_page.addRecord( "THIS IS A TEST #5".getBytes() ) ;
     *  RecordSetIterator m_records = m_page.getRecordSetIterator() ;
     *  m_records.reset() ;
     *  while( m_records.hasMoreRecords()) {
     *  byte[] data = m_records.getNextRecord() ;
     *  System.out.println( "ITERATOR GOT RECORD: " + Util.toHexString( data ) ) ;
     *  }
     */
    /*
     *  BASIC OPERATIONS TESTING
     *  Page freepage = master_volume.getFreePage() ;
     *  byte[] recid = freepage.addRecord( "THIS IS A TEST".getBytes() ) ;
     *  / recid format:  volid(4 bytes)-pageid(2 bytes)-blockid(2 bytes)
     *  ByteBuffer recid_buf = ByteBuffer.wrap(recid);
     *  int rec_vol_id = recid_buf.getInt() ;
     *  int rec_page_id = (int)recid_buf.getShort();
     *  Page datapage = master_volume.getPage( rec_page_id ) ;
     *  byte[] data = datapage.getRecord( recid ) ;
     *  byte[] recid2 = datapage.updateRecord( recid, "THIS IS A TEST #2".getBytes() ) ;
     *  byte[] data2 = datapage.getRecord( recid2 ) ;
     *  datapage.deleteRecord( recid2 ) ;
     */
  }



  /**
   *  Gets the page attribute of the Volume object
   *
   *@param  page_number  Description of Parameter
   *@return              The page value
   *@since
   */
  public ByteBuffer getPageBuffer(int page_number) {
    return vol_device.getPage(page_number);
    // todo, make this read-only
  }

  public ByteBuffer getPageBuffer(int page_number, int page_count ) {
	  return vol_device.getLargePages( page_number, page_count ) ;
  }


  /**
   *  Gets the blockBuffer attribute of the Volume object
   *
   *@param  block_number  Description of Parameter
   *@return               The blockBuffer value
   *@since
   */
  public ByteBuffer getBlockBuffer(int block_number) {
    return vol_device.getBlock(block_number);
    // todo, make this read-only
  }


  /**
   *  Gets the page attribute of the Volume object
   *
   *@param  page_number  Description of Parameter
   *@return              The page value
   *@since
   */
  public Page getPage(int page_number) {
    return new Page(vol_device.getPage(page_number), this);
  }


  /**
   *  Gets the freePage attribute of the Volume object
   *
   *@return    The freePage value
   *@since
   */
  public Page getFreePage() {
    return new Page(vol_device.getPage(vol_free_page), this);
  }


  /**
   *  Gets the freePage attribute of the Volume object
   *
   *@param  record_length  Description of Parameter
   *@return                The freePage value
   *@since
   */
  public Page getFreePage(long record_length) {
    return new Page(vol_device.getPage(vol_free_page), this);
    // todo, using record_length, may have to use large record strategy
    // note: large record is where record size > page size
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
   */
  public Page newFreePage() throws NoFreePageException {
    
    Page freepg = null ;
    
    try {

	 if (DEBUG)
		 printVolumeHeader() ;

	 // out of space
	 if (vol_num_pages == vol_max_extents)
		 throw new NoFreePageException() ;
      
      // check for extent size
      if ( vol_num_pages == vol_extent_size )		 
          extendVolume() ; 

      // Allocate Page
      vol_num_pages++;

      // Advance Free Page Counter. 
      vol_free_page++;

      // Vol Header
      flushVolumeHeader();

      // Page Header
      ByteBuffer pagebuffer = vol_device.getPage(vol_num_pages - 1);
      int page_hdrsize = Device.BLOCK_SIZE;
      // one block
      int page_number = vol_num_pages - 1;
      // page num
      int page_num_blocks = Device.PAGE_SIZE;
      // 512 blocks
      pagebuffer.clear();
      pagebuffer.putInt(page_hdrsize);
      pagebuffer.putInt(page_number);
      pagebuffer.putInt(page_num_blocks);
      pagebuffer.putInt(-1);// reserved
      pagebuffer.putInt(-1);// reserved

      // newly allocated page
      freepg = new Page(vol_device.getPage(vol_free_page), this);

	 if (DEBUG)
		 printVolumeHeader() ;

      
    } catch (Exception e) {
      System.out.println(e);
    }
    finally {
      return freepg ;
    }
  }


  public void close() {
    if (DEBUG)
	    printVolumeHeader() ; 
    vol_device.umount() ;
  }
  
  /**
   *@since
   *@link     dependency
   */
  /*
   *  #Page lnkPage;
   */
  private void printVolumeHeader() {
    System.out.println("===== START VOLUME HEADER =====");
    System.out.println("Header Size:   " + vol_hdr_size + " bytes ");
    System.out.println("Volume ID:     " + vol_id);
    System.out.println("Page Size:     " + vol_page_size + " bytes ");
    System.out.println("Block Size:    " + vol_block_size + " bytes ");
    System.out.println("Num Pages:     " + vol_num_pages + " pages allocated ");
    System.out.println("Extent Size:   " + vol_extent_size + " pages reserved ");
    System.out.println("Max Extents:   " + vol_max_extents + " max pages ");
    System.out.println("Free Page:     " + vol_free_page);
    System.out.println("===== END VOLUME HEADER =====");
  }


  /**
   *  Description of the Method
   *
   *@since
   */
  private void flushVolumeHeader() {
    try {

      // Vol Header
      ByteBuffer buffer = vol_device.getBlock(0);
      buffer.clear();
      buffer.putInt(vol_hdr_size);
      buffer.putInt(vol_id);
      buffer.putInt(vol_page_size);
      buffer.putInt(vol_block_size);
      buffer.putInt(vol_num_pages);
      buffer.putInt(vol_extent_size);
      buffer.putInt(vol_max_extents);
      buffer.putInt(vol_free_page);

    } catch (Exception e) {
      System.out.println(e);
    }
  }


  /**
   *  Description of the Method
   *
   *@since
   */
  private void allocateNewPage() {
    try {

      // Allocate Page
      vol_num_pages++;

      // Vol Header
      flushVolumeHeader();

      // Page Header
      ByteBuffer pagebuffer = vol_device.getPage(vol_num_pages - 1);
      int page_hdrsize = Device.BLOCK_SIZE;
      // one block
      int page_number = vol_num_pages - 1;
      // page num
      int page_num_blocks = Device.PAGE_SIZE;
      // 512 blocks
      pagebuffer.clear();
      pagebuffer.putInt(page_hdrsize);
      pagebuffer.putInt(page_number);
      pagebuffer.putInt(page_num_blocks);
      pagebuffer.putInt(-1);// reserved
      pagebuffer.putInt(-1);// reserved

    } catch (Exception e) {
      System.out.println(e);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  num_pages  Description of Parameter
   *@since
   */
  private void reservePages(int num_pages) {
    try {
      vol_device.getPage(num_pages - 1);// expands device if needed
    } catch (Exception e) {
      System.out.println(e);
    }
  }


  /**
   *  Description of the Method
   *
   *@since
   */
  private void extendVolume() { 
    try {
      vol_device.getPage(vol_max_extents - 1);
      this.vol_extent_size = this.vol_max_extents ;
    } catch (Exception e) {
      System.out.println(e);
    }  
  }
}

