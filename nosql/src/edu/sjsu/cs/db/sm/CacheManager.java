package edu.sjsu.cs.db.sm;

/**
 * class serves to implement the details of record caching code
 * CS 257 Project
 * @author Wallun Chan
 * Spring 2003
 */

import edu.sjsu.cs.db.sm.SM;

import java.util.*;

class CacheManager implements SM {


  public SM.OID getOID( byte[] oidbytes ) {
	return null ;
  }

	public CacheManager() {
		fileStorage = new SMImplVersion5();
	}
	public SM.OID store(SM.Record _rec) throws SM.IOException {
		return fileStorage.store(_rec);
	}
	public SM.Record fetch(SM.OID _oid) throws SM.NotFoundException, SM.IOException {
		SM.Record tmp;
		CacheData cData = (CacheData)map.get(_oid.getKey());
		if(cData != null) {
			cData.setBootFlag(false);
			tmp = cData.getRecord();
		}
		else {
			tmp = fileStorage.fetch(_oid);
			if(tmp == null)
				return tmp;
			if(map.size() == CACHE_LIMIT)
				boot();
			map.put(_oid.getKey(), new CacheData(tmp));
			vector.addElement(_oid.getKey());
		}
		return tmp;
	}
	public SM.OID update(SM.OID _oid, SM.Record _rec) throws SM.NotFoundException, SM.IOException {
		SM.OID oid = fileStorage.update(_oid, _rec);
		SM.Record record = fileStorage.fetch(oid);
		if(map.contains(_oid.getKey())) {
			map.remove(_oid.getKey());
			vector.remove(_oid.getKey());
		}
		else {
			if(map.size() == CACHE_LIMIT)
				boot();
		}
		map.put(oid.getKey(), new CacheData(record));
		vector.addElement(oid.getKey());
		return oid;
	}
	private void boot() {
		boolean loop = true;
		do {
			String id = (String)vector.remove(0);
			CacheData temp = (CacheData)map.get(id);
			if(temp.isBootable()) {
				map.remove(id);
				loop = false;
			}
			else {
				vector.addElement(id);
				temp.setBootFlag(true);
			}
		} while(loop);
	}
	public void delete(SM.OID _oid) throws SM.NotFoundException, SM.CannotDeleteException {
		vector.remove(_oid.getKey());
		map.remove(_oid.getKey());
		fileStorage.delete(_oid);
	}
	public void flush() {
	}
	public void close() throws IOException {
		map.clear();
		vector.clear();
		fileStorage.close();
	}
	private final int CACHE_LIMIT = 50;
	private Hashtable map = new Hashtable();	// Used for quick retrieval of records
	private Vector vector = new Vector();	// Used to order keys
	private SM fileStorage;
	private class CacheData {
		public CacheData(SM.Record record) {
			this.record = record;
		}
		public void setBootFlag(boolean flag) {
			bootFlag = flag;
		}
		public boolean isBootable() {
			return bootFlag;
		}
		public SM.Record getRecord() {
			return record;
		}
		private SM.Record record;
		private boolean bootFlag = false;
	}
}
