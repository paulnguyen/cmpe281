package edu.sjsu.cs.db.sm;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;


public class TestAcceptanceOIDTeam1 extends TestCase
{
    private static SM s_smInstance;
    private static List m_oidList = new ArrayList();

    public TestAcceptanceOIDTeam1( String _name )
    {
        super( _name );
    }

    public void testStoreOID()
    {
        try
        {
            SM.Record[] recordArray = new SM.Record[ 1000 ];
            for ( int i = 0; i < recordArray.length; i++ )
            {
                String recordInfo = "Record" + 1;
                SM.Record l_record = new SM.Record( recordInfo.length() );
                l_record.setBytes( recordInfo.getBytes() );
                recordArray[ i ] = l_record;
            }
            for ( int i = 0; i < recordArray.length; i++ )
            {
                SM.OID l_oid = s_smInstance.store( recordArray[i]);
                Assert.assertNotNull( l_oid );
                m_oidList.add( l_oid );
            }
        }
        catch( Exception e )
        {
            Assert.fail( e.getMessage() );
        }
    }

    public void testFetchOID()
    {
        try
        {
            String baseRecord = "Record";
            for ( int i = 0; i < m_oidList.size(); i++ )
            {
                SM.OID l_oid = ( SM.OID ) m_oidList.get( i );
                SM.Record l_record = s_smInstance.fetch( l_oid );
                String l_recordStringData = new String( l_record.data );
                String l_baseStringData = new String( baseRecord + 1 );
                if ( l_recordStringData.equals( l_baseStringData) == false )
                {
                    Assert.fail( "Stored data is different from retrieved");
                }
            }
        }
        catch( Exception e )
        {
            Assert.fail( e.getMessage() );
        }
    }

    public void testClose()
    {
        try
        {
            //s_smInstance.close();
        }
        catch( Exception e )
        {
            Assert.fail( e.getMessage() );
        }
    }

    protected void setUp ()
    {
        if ( s_smInstance == null )
        {
            s_smInstance = SMFactory.getInstance();
            m_oidList.clear();
        }
    }

    static public Test suite ()
    {
        TestSuite suite = new TestSuite();
        suite.addTest( new TestAcceptanceOIDTeam1( "testStoreOID" ) );
        suite.addTest( new TestAcceptanceOIDTeam1( "testFetchOID" ) );
        suite.addTest( new TestAcceptanceOIDTeam1( "testClose" ) );
        return suite;
    }
}
