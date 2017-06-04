package com.cloudstong.platform.core.cache.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.cloudstong.platform.core.cache.ICache;

public class MemoryCache implements ICache {
	
	private Map<String, Object> cache = Collections.synchronizedMap(new HashMap());

	public synchronized void add(String key, Object obj, int timeout) {
		cache.put(key, obj);
	}

	public synchronized void add(String key, Object obj) {
		cache.put(key, obj);
	}

	public synchronized void delByKey(String key) {
		cache.remove(key);
	}

	public void clearAll() {
		cache.clear();
	}

	public synchronized Object getByKey(String key) {
		return cache.get(key);
	}

	public boolean containKey(String key) {
		return cache.containsKey(key);
	}
}