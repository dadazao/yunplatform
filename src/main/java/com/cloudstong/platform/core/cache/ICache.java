package com.cloudstong.platform.core.cache;

public interface ICache {
	
	public void add(String key, Object value, int timeOut);

	public void add(String key, Object value);

	public void delByKey(String key);

	public void clearAll();

	public Object getByKey(String key);

	public boolean containKey(String key);
	
}