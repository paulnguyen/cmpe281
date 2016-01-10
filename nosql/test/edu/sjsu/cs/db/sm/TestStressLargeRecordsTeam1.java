package edu.sjsu.cs.db.sm;

import junit.framework.TestCase;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Random;
import java.util.Date;

public class TestStressLargeRecordsTeam1 extends TestCase
{
    private static SM s_smInstance;
    private static SM.OID s_oid;
    private static final int RECORD_SIZE = 3000000;

    public TestStressLargeRecordsTeam1( String _name )
    {
        super( _name );
    }

    public void testStoreLargeRecord()
    {
        try
        {
            byte[] l_recordData = getLargeRecord( RECORD_SIZE );
            SM.Record l_record = new SM.Record( l_recordData.length );
            l_record.setBytes( l_recordData );
            s_oid = s_smInstance.store( l_record );
        }
        catch( Exception e )
        {
            Assert.fail( e.getMessage() );
        }
    }

    public void testFetchLargeRecord()
    {
        try
        {
            SM.Record l_record = s_smInstance.fetch( s_oid );
            Assert.assertNotNull( l_record );
            if ( l_record.length() != RECORD_SIZE )
            {
                Assert.fail( "Stored record is different from excepted one");
            }
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
            //s_smInstance.close();
            s_smInstance = null;
            s_oid = null;
        }
        catch (Exception e)
        {
            Assert.fail( e.getMessage() );
        }
    }

    protected byte[] getLargeRecord( int _size )
    {
        byte[] result = new byte[ _size ];
        Random rm = new Random( new Date().getTime() );
        rm.nextBytes( result );
        return result;
    }

    protected void setUp ()
    {
        if ( s_smInstance == null )
        {
            s_smInstance = SMFactory.getInstance();
        }
    }

    static public Test suite ()
    {
        TestSuite suite = new TestSuite();
        suite.addTest( new TestStressLargeRecordsTeam1( "testStoreLargeRecord" ) );
        suite.addTest( new TestStressLargeRecordsTeam1( "testFetchLargeRecord" ) );
        suite.addTest( new TestStressLargeRecordsTeam1( "testClose" ) );
        return suite;
    }
}
