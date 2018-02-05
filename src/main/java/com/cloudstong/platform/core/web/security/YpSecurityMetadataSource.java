package com.cloudstong.platform.core.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.cloudstong.platform.core.cache.ICache;
import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.service.SysRoleService;
import com.cloudstong.platform.system.util.SecurityUtil;

public class YpSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, BeanPostProcessor {
	private SysRoleService sysRoleService;
	private ICache iCache;
	private HashSet<String> anonymousUrls = new HashSet();

	boolean isInit = false;

	public void setAnonymousUrls(HashSet<String> anonymousUrls) {
		this.anonymousUrls = anonymousUrls;
	}

	private String getUrl(String url, Set<String> params, String queryString) {
		boolean hasEmpty = false;

		for (String parameter : params) {
			if (StringUtil.isEmpty(parameter)) {
				hasEmpty = true;
			}
		}
		if (StringUtil.isEmpty(queryString)) {
			if (hasEmpty) {
				return url;
			}
			return "";
		}

		for (String parameter : params)
			if (!StringUtil.isEmpty(parameter)) {
				Set set = getParamsSet(parameter);
				Set queryStringSet = getParamsSet(queryString);

				if (queryStringSet.containsAll(set)) {
					url = url + "?" + parameter;
					return url;
				}
			}
		if (hasEmpty)
			return url;
		return "";
	}

	private Set<String> getParamsSet(String parameter) {
		Set set = new HashSet();
		String[] aryPara = parameter.split("&");
		for (String para : aryPara) {
			if (para.indexOf("=") > -1)
				set.add(para);
		}
		return set;
	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		Collection configAttributes = new HashSet();

		FilterInvocation filterInvocation = (FilterInvocation) object;
		HttpServletRequest request = filterInvocation.getRequest();

		String url = request.getRequestURI();
		url = removeCtx(url, request.getContextPath());

		if (anonymousUrls.contains(url)) {
			configAttributes.add(Constants.ROLE_CONFIG_ANONYMOUS);
			return configAttributes;
		}

		String queryString = request.getQueryString();

		Long systemId = Constants.DEFAULT_SYSTEM_ID;// SubSystemUtil.getCurrentSystemId(request);

		if (systemId == null) {
			configAttributes.add(Constants.ROLE_CONFIG_PUBLIC);
			return configAttributes;
		}

		boolean isCached = SecurityUtil.isResCached(systemId);

		if (!isCached) {
			SecurityUtil.loadRes(systemId);
		}

		Map roleMap = SecurityUtil.getUrlRoleMap(systemId);
		Map paraMap = SecurityUtil.getUrlParaMap(systemId);

		if ((roleMap == null) || (paraMap == null)) {
			configAttributes.add(Constants.ROLE_CONFIG_PUBLIC);
			return configAttributes;
		}

		url = url.replaceAll("/", "");
		if (!paraMap.containsKey(url)) {
			configAttributes.add(Constants.ROLE_CONFIG_PUBLIC);
			return configAttributes;
		}

		Set params = (Set) paraMap.get(url);

		String urlParams = getUrl(url, params, queryString);

		if ("".equals(urlParams)) {
			configAttributes.add(Constants.ROLE_CONFIG_PUBLIC);
		} else {
			configAttributes = (Collection)roleMap.get(urlParams);
		}
		return configAttributes;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	private static String removeCtx(String url, String contextPath) {
		url = url.trim();
		if (StringUtil.isEmpty(contextPath))
			return url;
		if (StringUtil.isEmpty(url))
			return "";
		if (url.startsWith(contextPath)) {
			url = url.replaceFirst(contextPath, "");
		}
		return url;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if ((bean instanceof SysRoleService)) {
			sysRoleService = ((SysRoleService) bean);
		}

		if ((bean instanceof ICache)) {
			iCache = ((ICache) bean);
		}

		if ((sysRoleService != null) && (iCache != null) && (!isInit)) {
			SecurityUtil.loadRes();
			isInit = true;
		}

		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}