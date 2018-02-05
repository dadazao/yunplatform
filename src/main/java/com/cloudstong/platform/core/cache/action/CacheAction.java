/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.core.cache.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cloudstong.platform.core.cache.ICache;
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.CacheUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.system.util.SecurityUtil;

/**
 * @author Allan
 * 
 *         Created on 2014-10-26
 * 
 *         Description:
 * 
 */
@ParentPackage("default")
@Namespace("/pages/system/cache")
@Results(value = { 
		@Result(name = "config", location = "/WEB-INF/view/system/cache/cacheRefresh.jsp")
})
public class CacheAction extends BaseAction {
	private String cacheName;
	
	@Action("config")
	public String config(){
		return "config";
		
	}
	
	@Action("refresh")
	public String refresh(){
		try {
			if("all".equals(cacheName)) {
				CacheUtil.clearAllSCache();
				CacheUtil.clearAllICache();
			}else{ 
				CacheUtil.clearSCache("domainCache");
				if("formCache".equals(cacheName)){
					CacheUtil.clearSCache(cacheName);
				}else if("tabulationCache".equals(cacheName)){
					CacheUtil.clearSCache(cacheName);
				}else if("themeCache".equals(cacheName)){
					CacheUtil.clearSCache(cacheName);
				}else if("desktopCache".equals(cacheName)){
					CacheUtil.clearSCache(cacheName);
				}else if("privilegeCache".equals(cacheName)){
					CacheUtil.clearSCache("privilegeCache");
					ICache icache = (ICache)AppUtil.getBean("iCache");
					icache.delByKey(SecurityUtil.UrlRoleMap + Constants.DEFAULT_SYSTEM_ID);
					icache.delByKey(SecurityUtil.UrlParaMap + Constants.DEFAULT_SYSTEM_ID);
				}
			}
			printJSON("success","刷新缓存成功！");
		} catch (Exception e) {
			if (log.isErrorEnabled()){
				log.error(e.getMessage(),e);
			}
			printJSON("fail","刷新缓存失败！");			
		}
		
		return NONE;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

}
