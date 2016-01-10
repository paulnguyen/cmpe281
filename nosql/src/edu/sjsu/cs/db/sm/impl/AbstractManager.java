package edu.sjsu.cs.db.sm.impl;

import edu.sjsu.cs.db.sm.SM;

import java.io.RandomAccessFile;
import java.io.File;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 */
abstract public class AbstractManager
{
    private final static String DATA_FILE_NAME = "file.data";    //default data file name
    private final static String INDEX_FILE_NAME = "file.index";  //default index file name
    private final static int DEFAULT_PAGE_SIZE  = 512;          //default page size
    public final static boolean DEBUG = true;                   //debug enable, should change to use log4j

    protected RandomAccessFile m_indexFile;                     //random access index file
    protected RandomAccessFile m_dataFile;                      //random access data file

    protected byte m_byteBuffer[];                              //byte buffer that we operate upon
    protected int m_pageSize = 0;                               //page size
    protected int m_nextPage = 0;                               //default next page
    protected TreeSet m_emptyPages = new TreeSet();             //tree of emtpy pages
    protected HashMap m_pageIndex = new HashMap();              //hashmap of page index
    protected final static long FILE_BEGIN   = 0l;

    protected AbstractManager() throws Exception
    {
        try
        {
            initFiles();
        }
        catch( Exception e )
        {

        }
    }

    public abstract int store( byte _record[] ) throws Exception;

    public abstract int update ( int _oid, byte _record[] ) throws Exception;

    public abstract void delete ( int _oid ) throws Exception;

    public abstract byte[] find ( int _oid ) throws Exception;


    /**
     * initializes index and data files, creates them if necessary
     * if index file is present, initialize it's properties
     * @throws Exception
     */
    protected void initFiles() throws Exception
    {
        File l_indexFile = new File( getIndexFileName() );
        boolean indexCreated = false;
        if ( l_indexFile.exists() == false )
        {
            l_indexFile.createNewFile();
            indexCreated = true;
            if ( DEBUG ) System.out.println( "Creating new Index File at="+l_indexFile.getAbsolutePath() );
        }
        File l_dataFile = new File( DATA_FILE_NAME );
        if ( l_dataFile.exists() == false )
        {
            l_dataFile.createNewFile();
            if ( DEBUG ) System.out.println( "Creating new Data File at="+l_dataFile.getAbsolutePath() );
        }
        m_indexFile = new RandomAccessFile( l_indexFile, "rw" );
        m_dataFile = new RandomAccessFile( l_dataFile, "rw" );
        if ( indexCreated == false )
        {
            initIndexProperties();
        }
        else
        {
            m_pageSize = DEFAULT_PAGE_SIZE;
        }
        m_byteBuffer = new byte[m_pageSize];    //initialize buffer with given page size
    }

    /**
     * initializes index properties
     * @throws Exception
     */
    protected void initIndexProperties() throws Exception
    {
        m_pageSize = m_indexFile.readInt(); //read page size from index file ( position 1 )
        m_nextPage = m_indexFile.readInt(); //read next page index
        if ( DEBUG ) System.out.println( "Read from index file, pageSize="+m_pageSize+" nextPage="+m_nextPage );
        m_pageSize = m_pageSize == 0 ? DEFAULT_PAGE_SIZE : m_pageSize;
        int emptyPages = m_indexFile.readInt(); //read empty pages
        if ( DEBUG ) System.out.println( "Empty pages="+emptyPages );
        for ( int i = 0; i < emptyPages; i++ ) //add empty pages to tree
        {
            m_emptyPages.add( new Integer( m_indexFile.readInt() ) );
        }
        int populated_pages = m_indexFile.readInt();//read populated pages
        if ( DEBUG ) System.out.println( "Populated pages="+populated_pages );
        for ( int i = 0; i < populated_pages; i++ )
        {
            int id = m_indexFile.readInt();     //read record id
            int recordLength = m_indexFile.readInt(); //read record length
            Record l_record = new Record( recordLength );
            int pagesForRecord = m_indexFile.readInt(); //read pages for a given record
            for ( int j = 0; j < pagesForRecord; j++ )
            {
                l_record.addPage( m_indexFile.readInt() );
            }
            m_pageIndex.put( new Integer( id ), l_record );
        }
    }

    /**
     * overload if don't want to use default, otherwise INDEX_FILE_NAME is returned
     * @return index file name
     */
    protected String getIndexFileName()
    {
        return INDEX_FILE_NAME;
    }

    protected String getDataFileName()
    {
        return DATA_FILE_NAME;
    }

    public void commit() throws Exception
    {
        int pageId;
        int emptyPage;
        m_indexFile.seek( FILE_BEGIN );

        m_indexFile.writeInt( m_pageSize ); //write page size
        m_indexFile.writeInt( m_nextPage ); //write next page

        int emptyPages = m_emptyPages.size();

        m_indexFile.writeInt( emptyPages ); //write tree empty pages size
        Iterator it = m_emptyPages.iterator();
        while ( it.hasNext() )
        {
            emptyPage = (( Integer ) it.next()).intValue();
            m_indexFile.writeInt( emptyPage );
        }
        emptyPages = m_pageIndex.size();
        m_indexFile.writeInt( emptyPages );
        it = m_pageIndex.entrySet().iterator();
        while ( it.hasNext() )
        {
            Map.Entry me = ( Map.Entry ) it.next();
            pageId = (( Integer ) me.getKey()).intValue();
            m_indexFile.writeInt( pageId );
            Record record = ( Record ) me.getValue();
            emptyPages = record.length();
            m_indexFile.writeInt( emptyPages );

            emptyPages = record.m_pages.size();
            m_indexFile.writeInt( emptyPages );

            for ( int i = 0; i < emptyPages; i++ )
            {
                m_indexFile.writeInt( (( Integer ) record.m_pages.get( i )).intValue() );
            }
        }
    }

    protected Record findRecordIndex( int _oid )
    {
        return ( Record ) m_pageIndex.get( new Integer( _oid ) );
    }

    class Record
    {
        protected int m_size;
        protected ArrayList m_pages = new ArrayList();

        Record()
        {

        }
        Record ( int size ) throws Exception
        {
            m_size = size;
        }


        protected int length ()
        {
            return m_size;
        }

        protected void addPage( int _pageIndex )
        {
            m_pages.add( new Integer( _pageIndex ) );
        }
    }
}
