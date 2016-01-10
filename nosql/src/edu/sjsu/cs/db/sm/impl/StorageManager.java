package edu.sjsu.cs.db.sm.impl;

import edu.sjsu.cs.db.sm.SM;

import java.nio.channels.FileChannel;
import java.nio.channels.Channel;
import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

public class StorageManager extends AbstractManager
{
    public static void main ( String args[] )
    {
        try
        {
            StorageManager sm = new StorageManager();
            String data = "Test";

            int id = sm.store( data.getBytes() );
//            sm.commit();
//            id = sm.update( 22, "Updated".getBytes());
//            System.out.println( "New id="+id );
            byte result[] = sm.find( id );
            System.out.println( "Size of result="+result.length );
//            System.out.println( "OID="+id+" Result=" + new String( result ).toString() );
//            sm.delete( id );
//            try
//            {
//                result = sm.find( id );
//            }
//            catch( SM.NotFoundException e )
//            {
//                System.out.println( "Correct, should not be found" );
//            }
            StringBuffer output = new StringBuffer();
            for ( int i = 0; i < result.length;i++)
            {
                output.append( (char)result[i]);
            }
            System.out.println( "Result=" + output.toString());
            sm.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public StorageManager () throws Exception
    {
        super();
    }


    public int store ( byte _record[] ) throws Exception
    {
        if ( false ) System.out.println( "Came to store" );

        Record l_localRecord = new Record( _record.length );
        if ( false ) System.out.println( "\trecord length=" + _record.length + " bytes" );
        int availablePage;
        long recordLength = _record.length;
        int currentIndex = 0;
        while ( recordLength > 0 )
        {
            if ( !m_emptyPages.isEmpty() ) //look for emtpy page
            {
                Integer i = ( Integer ) m_emptyPages.first();
                m_emptyPages.remove( i );
                availablePage = i.intValue();
                if ( false ) System.out.println( "1 availablePage=" + availablePage );
            }
            else
            {
                availablePage = m_nextPage;
                m_nextPage++;
                //System.out.println( "availablePage=" + availablePage );
            }
            //lets break data into pages
            long pageLength = (recordLength > m_pageSize) ? m_pageSize : recordLength;
            if ( false ) System.out.println( "cIndex=" + currentIndex + " pageLength=" + pageLength );
            System.arraycopy( _record, currentIndex, m_byteBuffer,0, (int)pageLength );
//            copyBytes( _record, currentIndex, pageLength );
//            if ( false )
//                System.out.println( "Size of byte buffer=" + m_byteBuffer.length +
//                                    " byteBuffer=" + new String( m_byteBuffer ).toString() );
            writeToFile( availablePage );
            currentIndex += pageLength;
            recordLength -= pageLength;
            l_localRecord.m_pages.add( new Integer( availablePage ) );
        }
        Integer i = ( Integer ) l_localRecord.m_pages.get( 0 );
        m_pageIndex.put( i, l_localRecord );
        return i.intValue();
    }

    private void writeToFile ( int _availablePage ) throws Exception
    {
        long offset = _availablePage * m_pageSize;
        m_dataFile.seek( offset );
        m_dataFile.write( m_byteBuffer );
    }

    private void copyBytes ( byte _record[], int _startIndex, long length )
    {
        for ( int i = 0; i < length; i++ )
        {
            m_byteBuffer[i] = _record[_startIndex + i];
        }
    }

    public int update ( int _oid, byte _record[] ) throws Exception
    {
        Record storedRecord = findRecordIndex( _oid );
        if ( storedRecord == null ) throw new SM.NotFoundException();
        m_pageIndex.remove( new Integer( _oid ) );
        Record newRecord = new Record( _record.length );
        int currentIndex = 0;
        int currentPage;
        int recordLength = _record.length;
        int currentPageLenth;
        int totalPages = 0;

        while ( recordLength > 0 )
        {
            if ( totalPages < storedRecord.m_pages.size() )
            {
                currentPage = (( Integer ) storedRecord.m_pages.get( totalPages )).intValue();
                totalPages++;
            }
            else if ( !m_emptyPages.isEmpty() )
            {
                Integer i = ( Integer ) m_emptyPages.first();
                m_emptyPages.remove( i );
                currentPage = i.intValue();
            }
            else
            {
                currentPage = m_nextPage;
                m_nextPage++;
            }

            currentPageLenth = (recordLength > m_pageSize) ? m_pageSize : recordLength;
            System.arraycopy( _record, currentIndex, m_byteBuffer, 0, currentPageLenth );

            m_dataFile.seek( currentPage * m_pageSize );
            m_dataFile.write( m_byteBuffer );

            currentIndex += currentPageLenth;
            recordLength -= currentPageLenth;
            newRecord.m_pages.add( new Integer( currentPage ) );
        }

        while ( totalPages < storedRecord.m_pages.size() )
        {
            m_emptyPages.add( storedRecord.m_pages.get( totalPages ) );
            totalPages++;
        }
        Integer i = ( Integer ) newRecord.m_pages.get( 0 );
        m_pageIndex.put( i, newRecord );
        return i.intValue();
    }

    public void delete ( int _oid ) throws Exception
    {
        Record record = findRecordIndex( _oid );
        if ( record == null ) throw new SM.NotFoundException();
        m_pageIndex.remove( new Integer( _oid ) );
        for ( int i = 0; i < record.m_pages.size(); i++ )//release all pages for a given id
        {
            m_emptyPages.add( record.m_pages.get( i ) );
        }
    }

    public byte[] find ( int _oid ) throws Exception
    {
        Record record = findRecordIndex( _oid );
        if ( record == null ) throw new SM.NotFoundException();
        int nextPage = 0;
        int totalPages = record.m_pages.size();
        byte[] data = new byte[record.length()];
        int currentIndex = 0;
        int currentLength;
        int recordLenth = record.length();
        do
        {
            long position = (( Integer ) record.m_pages.get( nextPage )).intValue();
            position = position * m_pageSize;
            m_dataFile.seek(  position );
            m_dataFile.read( m_byteBuffer );
            currentLength = (recordLenth > m_pageSize) ? m_pageSize : recordLenth;
            System.arraycopy( m_byteBuffer, 0, data, currentIndex, currentLength );
            currentIndex += currentLength;
            recordLenth -= currentLength;
            nextPage++;
        }
        while ( nextPage < totalPages );
        return data;
    }


}
