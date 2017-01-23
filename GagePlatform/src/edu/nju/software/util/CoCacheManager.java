package edu.nju.software.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


public class CoCacheManager {
	private static final String DEFAULT_KEY_NAME = "co_platform_cache";
	private static Cache cache = null;
	private static CacheManager cacheMaanager = null;
	
	public static Cache initCache() {
		if(null == cacheMaanager) {
			cacheMaanager = new CacheManager();
			int maxEntries = 100000;
			int ttl = 3600;
			int tti = 3600;
			Cache cache = new Cache(DEFAULT_KEY_NAME, maxEntries, false, false, ttl, tti);
			cacheMaanager.addCache(cache);
		}
		return cacheMaanager.getCache(DEFAULT_KEY_NAME);
	}
	
	public static void put(String key, Object value) {
		if(null == cache) {
			synchronized (CoCacheManager.class) {
				if(null == cache) {
					cache = initCache();
				}
			}
		}
		cache.put(new Element(key, value));
	}
	
	public static Object get(String key) {
		if(null == cache) {
			return null;
		}
		
		Element element = (Element) cache.get(key);
		if(null != element) {
			return element.getObjectValue();
		}else {
			return null;
		}
	}
	
	public static void remove(String key) {
		if(null == cache) {
			return;
		}
		cache.remove(key);
	}
}
