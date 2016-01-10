package edu.sjsu.cs.db.sm;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.Assert;

import edu.sjsu.cs.db.sm.*;
import java.util.*;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.BufferOverflowException;
import java.io.File;
import java.io.RandomAccessFile;

/**
 *  Performance test by loading a bunch of files 10 times into the Storage
 *  Manager.  Testing expects that the average time to store a record be less 
 *  than 100 miliseconds.  Setup a data directory under your test directory
 *  or under the directory were you run ant.  You can load the test files
 *  on Tigris at URL (4MB-ZIP):
 *  http://sjsu-cs257.tigris.org/files/documents/734/4115/data.zip
 *
 *@author     Paul Nguyen
 *@created    March 30, 2003
 */
public class TestXMLFileLoadTeam1 extends TestCase {

	private final static boolean DEBUG = false ;
	private final static int EXPECTED_AVERAGE_TIME = 100 ; // miliseconds	

	private static SM sm;


	/**
	 *  Constructor
	 *
	 *@param  name  Description of Parameter
	 *@since
	 */
	public TestXMLFileLoadTeam1(String name) {
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
		suite.addTest(new TestXMLFileLoadTeam1("testImport"));
		return suite;
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@since
	 */
	public void testImport() {

		FileChannel filechannel;
		MappedByteBuffer bytebuffer;
		RandomAccessFile thefile;
		long total_time = 1L ;
		long total_tests = 1L ;

		try {
			File plays = new File("data");
			File[] filelist = plays.listFiles();

			for (int k = 0; k < 10; k++) {
				for (int i = 0; i < filelist.length; i++) {
					if (filelist[i].isFile()) {
						thefile = new RandomAccessFile(filelist[i], "r");
						filechannel = thefile.getChannel();
						if (DEBUG)
							System.out.print("File: " + filelist[i].getName() + " - " + filechannel.size() + " bytes");
						bytebuffer = filechannel.map(
								FileChannel.MapMode.READ_ONLY,
								0,
								filechannel.size()
								);
						byte[] filedata = new byte[(int) filechannel.size()];
						bytebuffer.get(filedata, 0, (int) filechannel.size());
						SM.Record therec = new SM.Record((int) filechannel.size());
						therec.setBytes(filedata);
						Date start_ts = new Date() ;
						sm.store(therec);
						Date stop_ts = new Date() ;
						long diff = stop_ts.getTime() - start_ts.getTime()  ;
						total_time += diff ;	
						total_tests++ ;
						if (DEBUG)
							System.out.println( "---> Took " + diff + " miliseconds. " );
						thefile.close() ;
					}
				}
			}
			sm.close();

			if (true) {
				System.out.println( "Test Completed" ) ;
				System.out.println( "Total Records Stored: " + total_tests ) ;
				System.out.println( "Total Time Took: " + total_time + " miliseconds. ( " + total_time/1000 + " seconds )" ) ;
				System.out.println( "Average Time Per Record: " + (total_time/total_tests) + " miliseconds" ) ;
			}

			assertTrue( (total_time/total_tests) < EXPECTED_AVERAGE_TIME ) ; // avg transaction minimum

		} catch (Exception e) {
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

		} catch (Exception e) {
			Assert.fail("Unable to initialize test case");
		}

	}

}

