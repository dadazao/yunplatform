/*******************************************************************************
 * Licensed Materials - Property of Cloudstong
 * 
 * (C) Copyright Cloudstong Corp. 2014 All Rights Reserved. (
 *
 ******************************************************************************/
package com.cloudstong.platform.system.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;

import com.cloudstong.platform.core.cache.ICache;
import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.system.model.SubSystem;
import com.cloudstong.platform.system.model.SysResourceExtend;
import com.cloudstong.platform.system.model.SysRole;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SubSystemService;
import com.cloudstong.platform.system.service.SysResourceService;
import com.cloudstong.platform.system.service.SysRoleService;

/**
 * @author Allan
 * 
 *         Created on 2014-9-11
 * 
 *         Description:
 * 
 */
public class SecurityUtil {
	public static String FunctionRoleMap = "functionRoleMap_";

	public static String UrlRoleMap = "urlRoleMap_";

	public static String UrlParaMap = "urlParaMap_";

	public static String SystemRoleMap = "systemRoleMap_";

	public static String UserRole = "userRole_";

	public static String UserOrgRole = "userOrgRole_";

	public static String OrgRole = "orgRole_";

	public static String SystemFlag = "systemFlag_";

	public static void loadRes() {
		SubSystemService subSystemService = (SubSystemService) AppUtil.getBean(SubSystemService.class);
		List<SubSystem> sysList = subSystemService.getLocalSystem();
		if ((sysList == null) || (sysList.size() == 0))
			return;
		for (SubSystem sys : sysList) {
			Long systemId = Long.valueOf(sys.getSystemId());
			loadRes(systemId);
		}
	}

	public static Map<String, Collection<ConfigAttribute>> getUrlRoleMap(Long systemId) {
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		Map roleMap = (Map) iCache.getByKey(UrlRoleMap + systemId);
		return roleMap;
	}

	public static Map<String, Set<String>> getUrlParaMap(Long systemId) {
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		Map paraMap = (Map) iCache.getByKey(UrlParaMap + systemId);
		return paraMap;
	}

	public static void removeCacheBySystemId(Long systemId) {
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		iCache.delByKey(FunctionRoleMap + systemId);
		iCache.delByKey(UrlRoleMap + systemId);
		iCache.delByKey(UrlParaMap + systemId);
		iCache.delByKey(SystemRoleMap + systemId);

		iCache.delByKey(SystemFlag + systemId);

		loadRes(systemId);
	}

	private static void putFuncRoleList(Long systemId, List<SysResourceExtend> funcRoleList) {
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		String urlParaMapKey = FunctionRoleMap + systemId;
		Map functionRole = getResources(funcRoleList);
		if (BeanUtils.isNotEmpty(functionRole))
			iCache.add(urlParaMapKey, functionRole);
	}

	private static Map<String, Collection<ConfigAttribute>> getResources(List<SysResourceExtend> funcRoleList) {
		if (BeanUtils.isEmpty(funcRoleList))
			return null;
		Map functionRole = new HashMap();
		for (SysResourceExtend sre : funcRoleList)
			if (sre != null) {
				String function = sre.getFunc();
				String role = sre.getRole();
				if (!BeanUtils.isEmpty(function)) {
					function = function.trim();
					if (functionRole.containsKey(function)) {
						if (BeanUtils.isNotEmpty(role))
							((Collection) functionRole.get(function)).add(new SecurityConfig(role));
					} else {
						Collection set = new HashSet();
						if (BeanUtils.isNotEmpty(role))
							set.add(new SecurityConfig(role));
						functionRole.put(function, set);
					}
				}
			}
		return functionRole;
	}

	public static void loadRes(Long systemId) {
		SysResourceService sysResourceService = (SysResourceService) AppUtil.getBean(SysResourceService.class);
		SysRoleService sysRoleService = (SysRoleService) AppUtil.getBean(SysRoleService.class);
		List<SysResourceExtend> urlList = sysResourceService.getUrlRightMap(systemId);
		List<SysRole> roleList = sysRoleService.getRoleBySystemId(systemId);
		List<SysResourceExtend> funcRoleList = sysResourceService.getFunctionRoleList(systemId);

		putResources(systemId, urlList);
		putSystemRole(systemId, roleList);
		putFuncRoleList(systemId, funcRoleList);
	}

	public static boolean isResCached(Long systemId) {
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		Map map = (Map) iCache.getByKey(UrlRoleMap + systemId);
		if (map == null)
			return false;
		return true;
	}

	private static void putResources(Long systemId, List<SysResourceExtend> urlList) {
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		String urlRoleMapKey = UrlRoleMap + systemId;
		String urlParaMapKey = UrlParaMap + systemId;

		Map urlRoleMap = new HashMap();
		Map urlParaMap = new HashMap();

		if (BeanUtils.isEmpty(urlList)) {
			return;
		}

		for (SysResourceExtend resource : urlList) {
			if (resource != null) {
				String fullUrl = resource.getResourceUrl();
				String role = resource.getRole();

				if (!BeanUtils.isEmpty(fullUrl)) {
					fullUrl = fullUrl.replaceAll("/", "").trim();

					String parameter = "";
					String url = fullUrl;

					if (fullUrl.indexOf("?") > -1) {
						String[] aryUrl = fullUrl.split("\\?");
						url = aryUrl[0];
						parameter = aryUrl[1];
					}

					if (urlParaMap.containsKey(url)) {
						Set paramList = (Set) urlParaMap.get(url);
						paramList.add(parameter);
					} else {
						Set paramList = new HashSet();
						paramList.add(parameter);
						urlParaMap.put(url, paramList);
					}

					if (urlRoleMap.containsKey(fullUrl)) {
						Collection roleList = (Collection) urlRoleMap.get(fullUrl);
						if (BeanUtils.isNotEmpty(role))
							roleList.add(new SecurityConfig(role));
					} else {
						Set set = new HashSet();
						if (BeanUtils.isNotEmpty(role))
							set.add(new SecurityConfig(role));
						urlRoleMap.put(fullUrl, set);
					}
				}
			}
		}
		if (BeanUtils.isNotEmpty(urlRoleMap)) {
			iCache.add(urlRoleMapKey, urlRoleMap);
		}
		if (BeanUtils.isNotEmpty(urlParaMap)) {
			iCache.add(urlParaMapKey, urlParaMap);
		}
	}

	private static void putSystemRole(Long systemId, List<SysRole> RoleList) {
		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		String systemRoleKey = SystemRoleMap + systemId;

		Set roleSet = new HashSet();
		for (SysRole role : RoleList) {
			roleSet.add(role.getAlias());
		}
		iCache.add(systemRoleKey, roleSet);
	}

	public static void removeUserRoleCache(Long userId) {
		ICache cache = (ICache) AppUtil.getBean(ICache.class);
		String key = UserRole + userId;
		cache.delByKey(key);
	}

	public static void removeOrgRoleCache(Long orgId) {
		ICache cache = (ICache) AppUtil.getBean(ICache.class);
		String key = OrgRole + orgId;
		cache.delByKey(key);
	}

	public static void removeAll() {
		ICache cache = (ICache) AppUtil.getBean(ICache.class);
		cache.clearAll();
	}

	public static boolean hasFuncPermission(Long systemId, String function) {
		ICache cache = (ICache) AppUtil.getBean(ICache.class);
		Map functionRole = (Map) cache.getByKey(FunctionRoleMap + systemId);
		SysUser currentUser = AppContext.getCurrentUser();

		if (currentUser.getAuthorities().contains(Constants.ROLE_GRANT_SUPER)) {
			return true;
		}

		if ((functionRole != null) && (functionRole.containsKey(function))) {
			Collection configAttributes = (Collection) functionRole.get(function);
			for (GrantedAuthority hadRole : currentUser.getAuthorities()) {
				if (configAttributes.contains(new SecurityConfig(hadRole.getAuthority()))) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
}