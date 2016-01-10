package edu.sjsu.cs.db.sm;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.Assert;

import edu.sjsu.cs.db.sm.*;

/**
 *  Description of the Class
 *
 *@author     Paul Nguyen
 *@created    March 30, 2003
 */
public class TestAcceptanceCrossFunctionalTeam1 extends TestCase {

  private static SM sm;

  private static SM.OID rec1_oid = null;
  private static SM.OID rec2_oid = null;
  private static SM.OID rec3_oid = null;
  private static SM.Record rec1 = null;
  private static SM.Record rec2 = null;
  private static SM.Record rec3 = null;


  /**
   *  Constructor for the TestSMImplVersion1 object
   *
   *@param  name  Description of Parameter
   *@since
   */
  public TestAcceptanceCrossFunctionalTeam1(String name) {
    super(name);
  }


  /**
   *  A unit test suite for JUnit
   *
   *@return    The test suite
   *@since
   */
  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTest(new TestAcceptanceCrossFunctionalTeam1("testStoreAndFetch"));
    suite.addTest(new TestAcceptanceCrossFunctionalTeam1("testStoreAndDelete"));
    suite.addTest(new TestAcceptanceCrossFunctionalTeam1("testUpdate"));
    return suite;
  }


  /**
   *  A unit test for JUnit
   *
   *@since
   */
  public void testStoreAndFetch() {

    try {
      SM.Record found = null ;
      rec1_oid = (SM.OID) sm.store(rec1);
      found = sm.fetch(rec1_oid);
      assertNotNull( found ) ;              
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
  
  }



  /**
   *  A unit test for JUnit
   *
   *@since
   */
  public void testUpdate() { 
  
      SM.Record rec1_data = null ;
      SM.Record rec3_data = null ;
  
      try {
        rec1_data = sm.fetch(rec1_oid) ;
        rec3_oid = (SM.OID) sm.update(rec1_oid, rec3);
        rec3_data = sm.fetch(rec3_oid);	
      } 
      catch (SM.NotFoundException nfe) {
        Assert.fail("Update of rec1 with rec3 failed!");
      }
      catch (Exception e) {
        Assert.fail(e.getMessage());
      }


      if ( rec1_data.equals(rec3_data) )
        	Assert.fail("Failed Update Check. Rec1 Found!") ;
  
  }


  /**
   *  A unit test for JUnit
   *
   *@since
   */
  public void testStoreAndDelete() { 
  
     try {
        SM.Record found = null ;
        rec2_oid = (SM.OID) sm.store(rec2);
        found = sm.fetch(rec2_oid);
        sm.delete(rec2_oid);
        found = sm.fetch(rec2_oid); // should throw exception
        Assert.fail( "Delete failed" ) ;
      } catch (SM.NotFoundException nfe) {
        // Succcess
      }
      catch (Exception e) {
        Assert.fail(e.getMessage());
      }
  
  
  }


  /**
   *  The JUnit setup method
   *
   *@since
   */
  protected void setUp() {

    if (sm == null) {
      sm = SMFactory.getInstance();
    }

    try {
      // create some records for testing
      rec1 = new SM.Record(20);
      rec2 = new SM.Record(20);
      rec3 = new SM.Record(20);
      rec1.setBytes("This is a test #1".getBytes());
      rec2.setBytes("This is a test #2".getBytes());
      rec3.setBytes("This is a test #3".getBytes());
    } catch (Exception e) {
      Assert.fail("Unable to initialize test case");
    }

  }

}

