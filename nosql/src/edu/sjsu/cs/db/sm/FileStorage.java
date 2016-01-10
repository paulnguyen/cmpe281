package edu.sjsu.cs.db.sm;

import edu.sjsu.cs.db.sm.SM;
import edu.sjsu.cs.db.sm.impl.StorageManager;

import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.BufferOverflowException;
import java.io.File;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

public class FileStorage implements SM
{
    private StorageManager m_storageManager = null;
//    private FileChannel m_fileChannel;
//    private MappedByteBuffer m_mappedByteBuffer;
//    private static final String FILE_NAME = "file.data";
//    private static final long MAX_FILE_SIZE = 100000000;


  public SM.OID getOID( byte[] oidbytes ) {
	return null ;
  }

  public void flush() {
	  try {
		  close() ;
	  }
	  catch ( Exception e ) {}
  }


    public static void main ( String args[] )
    {
        FileStorage m_fileStore = new FileStorage();
        try
        {
            SM.Record record = new SM.Record( "test storage1".length() );
            record.setBytes( "test storage1".getBytes() );
            m_fileStore.store( record );

            SM.Record record1 = new SM.Record( "LLL".length() );
            record1.setBytes( "LLL".getBytes() );
            m_fileStore.store( record1 );

            SM.Record record2 = new SM.Record( "TEST MESSAGE".length() );
            record2.setBytes( "TEST MESSAGE".getBytes() );
            m_fileStore.store( record2 );

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        m_fileStore.store( "TEST STRING");
    }

    public FileStorage ()
    {
        try
        {
            init();
        }
        catch( Exception e)
        {
            e.printStackTrace();
        }
    }

    public SM.OID store ( SM.Record _rec ) throws SM.IOException
    {
        if ( _rec == null || _rec.data == null ) throw
                new SMExceptionImpl( new IllegalArgumentException ( "Record cannot be null") );
        SM.OID l_oid = null;
        try
        {
            int id = m_storageManager.store( _rec.data );
            l_oid = new OID( id );
        }
        catch( Throwable e )
        {
            e.printStackTrace();
            throw new SMExceptionImpl( e );
        }
        return l_oid;
    }


    public SM.Record fetch ( SM.OID _oid ) throws SM.NotFoundException, SM.IOException
    {
        if ( _oid == null ) throw new SMExceptionImpl( new IllegalArgumentException( "OID cannot be null" ) );
        FileStorage.OID l_oid = ( FileStorage.OID ) _oid;
        SM.Record l_record = null;
        {
            try
            {
                byte[] result = m_storageManager.find( l_oid.getIntKey() );

                l_record = new FileRecord( result.length );
                l_record.setBytes( result );
            }
            catch( SM.NotFoundException e )
            {
                e.printStackTrace();
                throw e;
            }
            catch( SM.SMException e )
            {
                e.printStackTrace();
                throw new SMExceptionImpl( e );
            }
            catch( Throwable e )
            {
                e.printStackTrace();
                throw new SMExceptionImpl( e );
            }
        }
        return l_record;
    }


    public SM.OID update ( SM.OID _oid, SM.Record _rec ) throws SM.NotFoundException, SM.IOException
    {
        FileStorage.OID resultOID = null;
        try
        {
            FileStorage.OID l_oid = ( FileStorage.OID ) _oid;
            int id = m_storageManager.update( l_oid.getIntKey(), _rec.data );
            resultOID = new FileStorage.OID( id );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
        return resultOID;
    }

    public void delete ( SM.OID _oid ) throws SM.NotFoundException, SM.CannotDeleteException
    {
        FileStorage.OID l_oid = ( FileStorage.OID ) _oid;
        try
        {
            m_storageManager.delete( l_oid.getIntKey() );
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            throw new SM.CannotDeleteException();
        }
    }

    public void close() throws IOException
    {
        try
        {
            m_storageManager.commit();
        }
        catch( Exception e )
        {
            e.printStackTrace();
            throw new SMExceptionImpl( e );
        }
    }

    private void init() throws Exception
    {
        try
        {
            m_storageManager = new StorageManager();
        }
        catch( Throwable e )
        {
            e.printStackTrace();
            throw new Exception( e.getMessage() );
        }
    }

//    private MappedByteBuffer allocateBuffer() throws java.io.IOException
//    {
//        return allocateBuffer( MAX_FILE_SIZE );
//    }
//
//    private MappedByteBuffer allocateBuffer( long _fileSize ) throws java.io.IOException
//    {
//        long l_position = 0;
//        return m_fileChannel.map( FileChannel.MapMode.READ_WRITE, l_position, _fileSize );
//    }

    public class FileRecord extends SM.Record
    {
        protected byte[] data;
        public FileRecord( int size ) throws SMException
        {
            super( size );
        }

        public boolean equals ( Object o )
        {
            System.out.println( "Came to equals" );
            if ( this == o )
            {
                return true;
            }
            if ( !(o instanceof FileRecord) ) return false;

            final FileRecord l_fileRecord = ( FileRecord ) o;

            if ( !Arrays.equals( data, l_fileRecord.data ) ) return false;

            return true;
        }

//        public int hashCode ()
//        {
//            return 0;
//        }

    }

    public class OID implements edu.sjsu.cs.db.sm.SM.OID
    {
        private int key;

        /**
         *  Constructor for the OID object
         *
         *@param  key  Description of Parameter
         */
        public OID ( int key )
        {
            this.key = key;
        }

        public int getIntKey()
        {
            return key;
        }

        /**
         *  Gets the key attribute of the OID object
         *
         *@return    The key value
         */
        public String getKey ()
        {
            return Integer.toString( this.key );
        }

        public byte[] toBytes()
        {
            return getKey().getBytes();
        }

    }

//    private byte[] getKey( byte[] data)
//    {
//        byte digest[] = null;
//        try
//        {
//            MessageDigest mesgdigest = MessageDigest.getInstance( "SHA" );
//            mesgdigest.update( data );
//             digest = mesgdigest.digest();
//        }
//        catch( Exception e )
//        {
//            e.printStackTrace();
//        }
//        return digest;
//    }

    public void printBuffer ( String prefix, ByteBuffer buffer )
            throws Exception
    {
        System.out.print( prefix + ": '" );

        int nulls = 0;
        int limit = buffer.limit();

        for ( int i = 0; i < limit; i++ )
        {
            char c = ( char ) buffer.get( i );

            if ( c == '\u0000' )
            {
                nulls++;
                continue;
            }

            if ( nulls != 0 )
            {
                System.out.print( "|[" + nulls
                                  + " nulls]|" );
                nulls = 0;
            }

            System.out.print( c );
        }

        System.out.println( "'" );
    }


}
