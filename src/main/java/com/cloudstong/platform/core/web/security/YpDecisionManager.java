package com.cloudstong.platform.core.web.security;

import java.util.Calendar;
import java.util.Collection;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.util.SecurityUtil;

public class YpDecisionManager implements AccessDecisionManager {
	public Logger logger = LoggerFactory.getLogger(YpDecisionManager.class);

	private static boolean isInit = false;

	private static int isValid = -3;

	@Resource
	private Properties configproperties;

	private synchronized int validKey() {
		if (isInit) {
			return isValid;
		}

		isInit = true;

		String productKey = (String) configproperties.get("productKey");

		if (StringUtil.isEmpty(productKey)) {
			isValid = -1;
			return -1;
		}
		try {
			if (isFirstEvaluate(productKey)) {
				isValid = 0;
				return 0;
			}
			String[] aryProductKey = productKey.split(",");

			if (aryProductKey[0].equals("1")) {
				isValid = 0;
				return 0;
			}

			Long startTime = Long.valueOf(Long.parseLong(aryProductKey[1]));
			Long stopTime = Long.valueOf(Long.parseLong(aryProductKey[2]));
			Long currentTime = Long.valueOf(System.currentTimeMillis());
			if ((currentTime.longValue() > startTime.longValue()) && (currentTime.longValue() < stopTime.longValue())) {
				isValid = 0;
				return 0;
			}

			isValid = -3;
			return -3;
		} catch (Exception ex) {
			isValid = -2;
		}
		return -2;
	}

	private boolean isFirstEvaluate(String productKey) {
		return true;
	}

	private String generateEvaluateKey() {
		Long startTime = Long.valueOf(System.currentTimeMillis());

		Calendar calendar = Calendar.getInstance();
		calendar.add(5, 30);
		Long endTime = Long.valueOf(calendar.getTimeInMillis());
		String key = "0," + startTime + "," + endTime;
		return key;
	}

	private String getMessage(int i) {
		String msg = "";
		switch (i) {
		case -1:
			msg = "没有产品key,请购买正式版产品!";
			break;
		case -2:
			msg = "有效期已过,请购买正式版产品!";
		case -3:
			msg = "有效期已过,请购买正式版产品!";
		}

		return msg;
	}

	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException,
			InsufficientAuthenticationException {
		if (configAttributes.contains(Constants.ROLE_CONFIG_ANONYMOUS)) {
			return;
		}

		// int rtn = validKey();
		// if (rtn != 0) {
		// String msg = getMessage(rtn);
		// throw new AccessDeniedException(msg);
		// }

		if (authentication == null) {
			throw new AccessDeniedException("没有登录系统");
		}

		Object principal = authentication.getPrincipal();
		if (principal == null) {
			throw new AccessDeniedException("登录对象为空");
		}

		if (!(principal instanceof SysUser)) {
			throw new AccessDeniedException("登录对象必须为SysUser实现类");
		}

		SysUser user = (SysUser) principal;

		Collection<GrantedAuthority> roles = user.getAuthorities();

		String mes = "云快速开发与管理平台 >>>>\nURL:" + object + "\n当前用户拥有角色:" + roles + "\n当前URL被分配给以下角色:" + configAttributes;

		logger.debug(mes);

		if (roles.contains(Constants.ROLE_GRANT_SUPER)) {
			return;
		}

		if (configAttributes.contains(Constants.ROLE_CONFIG_PUBLIC)) {
			return;
		}

		for (GrantedAuthority hadRole : roles) {
			if (configAttributes.contains(new SecurityConfig(hadRole.getAuthority()))) {
				return;
			}
		}

		throw new AccessDeniedException("对不起，你没有访问该页面的权限！");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}