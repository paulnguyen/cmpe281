
package edu.sjsu.cs.db.sm;

public interface RecordSetIterator {
    void   reset() ;
    byte[] getNextRecord();
    boolean hasMoreRecords() ;
}
