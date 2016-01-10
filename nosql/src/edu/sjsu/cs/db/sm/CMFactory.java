package edu.sjsu.cs.db.sm;

/**
 * class serves as a factory to get access to CacheManager implementation
 *
 * @author Wallun Chan
 */

public class CMFactory {
	private static SM cacheManager;
	public static SM getInstance() {
		if(cacheManager == null) {
			synchronized(SMImplVersion1.class) {
				if(cacheManager == null)
					cacheManager = new CacheManager();
			}
		}
        return cacheManager;
	}
    protected CMFactory() {
    }
}