package edu.sjsu.cs.db.sm;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.Assert;

import edu.sjsu.cs.db.sm.*;

public class TestAcceptanceBasicTeam1 extends TestCase
{
	private static SM s_smInstance;
	
	public TestAcceptanceBasicTeam1( String name )
	{
	    super( name );
	}
	
	public void testStore()
	{
		try
		{
            SM.Record recordToStore = new SM.Record( 20 );
            SM.OID storedOID = s_smInstance.store( recordToStore );
            Assert.assertNotNull( storedOID );
		}
		catch( Exception e )
		{
            Assert.fail( e.getMessage() );
		}
	}

    public void testFetch()
    {
        try
        {
            SM.Record recordToStore = new SM.Record( 30 );
            SM.OID storedOID = s_smInstance.store( recordToStore );
            Assert.assertNotNull( storedOID );
            SM.Record found = s_smInstance.fetch( storedOID );
            Assert.assertNotNull( found );
        }
        catch( Exception e )
        {
            Assert.fail( e.getMessage() );
        }
    }

    public void testClose ()
    {
        try
        {
            s_smInstance.close();
            s_smInstance = null;
//            s_oid = null;
        }
        catch (Exception e)
        {
            Assert.fail( e.getMessage() );
        }
    }

    public void testUpdate()
    {
        try
        {
            SM.Record recordToStore = new SM.Record( 40 );
            SM.Record recordToUpdate = new SM.Record( 41 );
            SM.OID storedOID = s_smInstance.store( recordToStore );
            Assert.assertNotNull( storedOID );
            SM.OID updatedOID = s_smInstance.update( storedOID, recordToUpdate );
            Assert.assertNotNull( storedOID );
            SM.Record updatedRecord = s_smInstance.fetch( updatedOID );
            Assert.assertEquals( recordToUpdate, updatedRecord );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

    public void testDelete()
    {
        try
        {
            boolean notBeFound = false;
            SM.Record recordToStore = new SM.Record( 42 );
            SM.OID storedOID = s_smInstance.store( recordToStore );
            s_smInstance.delete( storedOID );
            try
            {
                SM.Record shouldNotBeFound = s_smInstance.fetch( storedOID );
            }
            catch( SM.NotFoundException e )
            {
                notBeFound = true;
            }
            Assert.assertTrue( notBeFound );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            Assert.fail( e.getMessage() );
        }
    }

	protected void setUp()
	{ 
		if ( s_smInstance == null )
		{
			s_smInstance = SMFactory.getInstance();
		}
	}

	static public Test suite ()
	{
		TestSuite suite= new TestSuite();
		suite.addTest( new TestAcceptanceBasicTeam1( "testStore" ) );
		suite.addTest( new TestAcceptanceBasicTeam1( "testFetch" ) );
		suite.addTest( new TestAcceptanceBasicTeam1( "testUpdate" ) );
		suite.addTest( new TestAcceptanceBasicTeam1( "testDelete" ) );
		suite.addTest( new TestAcceptanceBasicTeam1( "testClose" ) );
		return suite;
	}

}
