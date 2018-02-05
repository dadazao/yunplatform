/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.cache;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;


/**
 * @author Allan
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class YpCacheManager extends SimpleCacheManager {
	
	public void clearAll() {
		Collection<Cache> caches = (Collection<Cache>)super.loadCaches();
		Iterator<Cache> it = caches.iterator();
		while(it.hasNext()) {
			Cache cache = it.next();
			cache.clear();
		}
	}

}
