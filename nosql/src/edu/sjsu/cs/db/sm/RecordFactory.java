package edu.sjsu.cs.db.sm;

/**
 * Created by IntelliJ IDEA.
 * User: denis.gefter
 * Date: May 13, 2003
 * Time: 12:15:56 AM
 * To change this template use Options | File Templates.
 */
public class RecordFactory
{
    public static SM.Record getRecord( int size) throws SM.SMException
    {
        return new SM.Record( size );
    }
}
