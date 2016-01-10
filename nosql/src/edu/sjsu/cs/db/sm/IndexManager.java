package edu.sjsu.cs.db.sm;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public class IndexManager
{
    private boolean DEBUG = false;
    private ByteBuffer translation_table = null; // guid to recid translation table
    private TreeSet m_indexPages = new TreeSet();

    public IndexManager ( ByteBuffer _translationTable )
    {
        if ( _translationTable == null ) throw new IllegalArgumentException( "Translation table is null " );
        translation_table = _translationTable;
        init();
    }


    public void commit () throws SM.IOException
    {
        Iterator it = m_indexPages.iterator();
        translation_table.clear();
        int i = 0;
        while ( it.hasNext() )
        {
            IndexRecord indexRecord = ( IndexRecord ) it.next();
            this.translation_table.put( indexRecord.getGuid() );
            this.translation_table.put( indexRecord.getRecid() );
            if ( DEBUG ) System.out.println( "Wrote record[" + i++ + "] to database" );
        }
    }

    private void init ()
    {
        ByteBuffer tbl = this.translation_table.duplicate();
        tbl.clear();
        m_indexPages.clear();
        byte[] guid = new byte[Volume.RECORD_GUID_SIZE];
        byte[] recid = new byte[Volume.RECORD_ID_SIZE];
        int i = 0;
        while ( tbl.position() < (tbl.limit() - Volume.RECORD_GUID_SIZE + Volume.RECORD_ID_SIZE) )
        {
            tbl.get( guid, 0, Volume.RECORD_GUID_SIZE );
            tbl.get( recid, 0, Volume.RECORD_ID_SIZE );
            if ( !("00.00.00.00.00.00.00.00".equals( Util.toHexString( recid ) )) )
            {
                if ( DEBUG ) System.out.println( "Creating tree, i=" + i++ );
                m_indexPages.add( new IndexRecord( guid, recid ) );
//                System.out.println( "GUID[" + Util.toHexString( guid ) + "] => RECID[" + Util.toHexString( recid ) + "] " );
            }

        }
    }

    public void trans_table_reset ()
    {
//        this.translation_table.clear();
//        ByteBuffer tbl = this.translation_table.duplicate();
//        tbl.clear();
//        byte[] guid = new byte[Volume.RECORD_GUID_SIZE];
//        byte[] recid = new byte[Volume.RECORD_ID_SIZE];
//        while ( tbl.position() < (tbl.limit() - Volume.RECORD_GUID_SIZE + Volume.RECORD_ID_SIZE) )
//        {
//            tbl.get( guid, 0, Volume.RECORD_GUID_SIZE );
//            tbl.get( recid, 0, Volume.RECORD_ID_SIZE );
//            if ( !("00.00.00.00.00.00.00.00".equals( Util.toHexString( recid ) )) )
//            {
//                int newpos = this.translation_table.position() + Volume.RECORD_GUID_SIZE + Volume.RECORD_ID_SIZE;
//                this.translation_table.position( newpos );
//            }
//        }
    }

    public byte[] trans_table_find_entry ( byte[] find_guid )
    {
        // try to avoid disk i/o
//        byte[] cachehit = ( byte[] ) lookupcache.get( find_guid );
//        if ( cachehit != null )
//            return cachehit;
        Iterator it = m_indexPages.iterator();
        while ( it.hasNext() )
        {
            IndexRecord record = ( IndexRecord ) it.next();
            if ( Arrays.equals( record.getGuid(), find_guid ) )
            {
                if ( DEBUG ) System.out.println( "Found in cache...,\nguid=" + Util.toHexString( find_guid ) + " recid=" + Util.toHexString( record.getRecid() ) );
                return record.getRecid();
            }
        }

//        ByteBuffer tbl = this.translation_table.duplicate();
//        tbl.clear();
//        byte[] guid = new byte[Volume.RECORD_GUID_SIZE];
//        byte[] recid = new byte[Volume.RECORD_ID_SIZE];
//        String findstr = Util.toHexString( find_guid );
//        System.out.println( "Started search for physical id" );
//        int i = 0;
//        while ( tbl.position() < this.translation_table.position() )
//        {
//            if ( DEBUG ) System.out.println( "Search["+i+++"]" );
//            tbl.get( guid, 0, Volume.RECORD_GUID_SIZE );
//            tbl.get( recid, 0, Volume.RECORD_ID_SIZE );
//            if ( DEBUG )
//            {
//                System.out.println( "Looking for: " + findstr ) ;
//                System.out.println( "Entry Value: " + Util.toHexString(guid) ) ;
//            }
//            if ( findstr.equals( Util.toHexString( guid ) ) )
//            {
//                m_indexPages.add( new IndexRecord( find_guid, recid ) );
//                return recid;
//            }
//        }
        //if (DEBUG)
        //  System.out.println( "Entry Not Found!  Return NULL" ) ;
        return null;
    }


    public void trans_table_add_entry ( byte[] guid, byte[] recid )
    {
        if ( DEBUG ) System.out.println( "Adding entry...\nguid=" + Util.toHexString( guid ) + "\nrecid=" + Util.toHexString( recid ) );
        IndexRecord indexRecord = new IndexRecord( guid, recid );
//        Iterator it = m_indexPages.iterator();
//        while (it.hasNext() )
//        {
//            System.out.println( "Object..........="+it.next().getClass() );
//        }
        m_indexPages.add( indexRecord );

//        this.translation_table.put( guid );
//        this.translation_table.put( recid );
//        lookupcache.put( guid, recid );
    }

    public void trans_table_update_entry ( byte[] find_guid, byte[] new_recid )
    {
        Iterator it = m_indexPages.iterator();
        while ( it.hasNext() )
        {
            IndexRecord indexRecord = ( IndexRecord ) it.next();
            if ( Arrays.equals( indexRecord.getGuid(), find_guid ) )
            {
                m_indexPages.remove( indexRecord );
                break;
            }
        }
        IndexRecord newIndexRecord = new IndexRecord( find_guid, new_recid );
        m_indexPages.add( newIndexRecord );
//        ByteBuffer tbl = this.translation_table.duplicate();
//        tbl.clear();
//        byte[] guid = new byte[Volume.RECORD_GUID_SIZE];
//        byte[] recid = new byte[Volume.RECORD_ID_SIZE];
//        String findstr = Util.toHexString( find_guid );
//        while ( tbl.position() < this.translation_table.position() )
//        {
//            tbl.get( guid, 0, Volume.RECORD_GUID_SIZE );
//            tbl.get( recid, 0, Volume.RECORD_ID_SIZE );
//            if ( findstr.equals( Util.toHexString( guid ) ) )
//            {
//                int newpos = tbl.position() - Volume.RECORD_ID_SIZE;
//                tbl.position( newpos );
//                tbl.put( new_recid );
////                lookupcache.put( guid, new_recid );
//                return;
//            }
//        }
    }

    public void trans_table_delete_entry ( byte[] find_guid )
    {
        Iterator it = m_indexPages.iterator();
        while ( it.hasNext() )
        {
            IndexRecord indexRecord = ( IndexRecord ) it.next();
            if ( Arrays.equals( indexRecord.getGuid(), find_guid ) )
            {
                m_indexPages.remove( indexRecord );
                break;
            }
        }
//        ByteBuffer tbl = this.translation_table.duplicate();
//        tbl.clear();
//        byte[] guid = new byte[Volume.RECORD_GUID_SIZE];
//        byte[] recid = new byte[Volume.RECORD_ID_SIZE];
//        String findstr = Util.toHexString( find_guid );
//        while ( tbl.position() < this.translation_table.position() )
//        {
//            tbl.get( guid, 0, Volume.RECORD_GUID_SIZE );
//            tbl.get( recid, 0, Volume.RECORD_ID_SIZE );
//            if ( findstr.equals( Util.toHexString( guid ) ) )
//            {
//                int newpos = tbl.position() - (Volume.RECORD_GUID_SIZE + Volume.RECORD_ID_SIZE);
//                tbl.position( newpos );
//                byte zero = 0;
//                Arrays.fill( guid, zero );
//                Arrays.fill( recid, zero );
//                tbl.put( guid );
//                tbl.put( recid );
////                lookupcache.remove( find_guid );
//                return;
//            }
//        }
    }

    public void trans_table_dump ()
    {
        System.out.println( "-- Translation Table --" );
        System.out.println( this.translation_table );
        ByteBuffer tbl = this.translation_table.duplicate();
        tbl.clear();
        byte[] guid = new byte[Volume.RECORD_GUID_SIZE];
        byte[] recid = new byte[Volume.RECORD_ID_SIZE];
        while ( tbl.position() < (tbl.limit() - Volume.RECORD_GUID_SIZE + Volume.RECORD_ID_SIZE) )
        {
            tbl.get( guid, 0, Volume.RECORD_GUID_SIZE );
            tbl.get( recid, 0, Volume.RECORD_ID_SIZE );
            if ( !("00.00.00.00.00.00.00.00".equals( Util.toHexString( recid ) )) )
            {
                System.out.println( "GUID[" + Util.toHexString( guid ) + "] => RECID[" + Util.toHexString( recid ) + "] " );
            }
        }
        System.out.println( "-- Translation Table --" );
    }

    class IndexRecord implements Comparable
    {
        private byte m_guid[];
        private byte m_recid[];

        public IndexRecord ( byte[] _guid, byte[] _recid )
        {
            m_guid = _guid;
            m_recid = _recid;
        }

        public byte[] getGuid ()
        {
            return m_guid;
        }

        public void setGuid ( byte[] _guid )
        {
            m_guid = _guid;
        }

        public byte[] getRecid ()
        {
            return m_recid;
        }

        public void setRecid ( byte[] _recid )
        {
            m_recid = _recid;
        }

        public int compareTo ( Object o )
        {
            IndexRecord ir = ( IndexRecord ) o;
            String irSt = Util.toHexString( ir.getGuid() );
            String thisSt = Util.toHexString( this.getGuid() );
            return thisSt.compareTo( irSt );
        }

        public boolean equals ( Object o )
        {
            if ( this == o ) return true;
            if ( !(o instanceof IndexRecord) ) return false;

            final IndexRecord l_indexRecord = ( IndexRecord ) o;

            if ( !Arrays.equals( m_guid, l_indexRecord.m_guid ) ) return false;
            if ( !Arrays.equals( m_recid, l_indexRecord.m_recid ) ) return false;

            return true;
        }

        public int hashCode ()
        {
            return 0;
        }

        public String toString ()
        {
            if ( DEBUG )
            {
                String result = Util.toHexString( this.getGuid() ) +
                        "\nRECID=" + Util.toHexString( this.getRecid() );
                return result;
            }
            else
            {
                return "";
            }
        }

    }
}
