/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.util;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.cache.Cache;

import com.cloudstong.platform.core.cache.ICache;
import com.cloudstong.platform.core.cache.YpCacheManager;
import com.google.code.ssm.spring.ExtendedSSMCacheManager;
import com.google.code.ssm.spring.SSMCache;

/**
 * @author Allan
 * 
 *         Created on 2014-10-23
 * 
 *         Description:
 * 
 */
public class CacheUtil {
	
	public static void clearSCache(String cacheName) {
		YpCacheManager ypcm = (YpCacheManager) AppUtil.getBean("ypCacheManager");
		Cache cache = ypcm.getCache(cacheName);
		cache.clear();
	}
	
	public static void clearAllSCache() {
		YpCacheManager ypcm = (YpCacheManager) AppUtil.getBean("ypCacheManager");
		ypcm.clearAll();
		
//		ExtendedSSMCacheManager esm = (ExtendedSSMCacheManager)AppUtil.getBean("ssmCacheManager");
//		Collection<SSMCache> sscs = esm.getCaches();
//		Iterator<SSMCache> it = sscs.iterator();
//		while(it.hasNext()) {
//			SSMCache cache = it.next();
//			cache.clear();
//		}
	}
	
	public static void clearICache(String key) {
		ICache iCache = (ICache)AppUtil.getBean("iCache");
		iCache.delByKey(key);
	}
	
	public static void clearAllICache() {
		ICache iCache = (ICache)AppUtil.getBean("iCache");
		iCache.clearAll();
	}
	
	
	

}
