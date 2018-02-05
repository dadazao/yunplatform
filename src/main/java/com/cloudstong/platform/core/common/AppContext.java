package com.cloudstong.platform.core.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cloudstong.platform.core.cache.ICache;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.CookieUitl;
import com.cloudstong.platform.core.util.RequestUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.SysOrg;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.SysOrgService;
import com.cloudstong.platform.system.service.SysUserService;
import com.cloudstong.platform.third.bpm.thread.MessageUtil;
import com.cloudstong.platform.third.bpm.thread.TaskThreadService;
import com.cloudstong.platform.third.bpm.thread.TaskUserAssignService;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:应用Context,包含当前用户相关的所有信息
 */
public final class AppContext {
	private static Logger logger = LoggerFactory.getLogger(AppContext.class);
	private static ThreadLocal<SysUser> curUser = new ThreadLocal();

	private static ThreadLocal<SysOrg> curOrg = new ThreadLocal();
	public static final String CurrentOrg = "CurrentOrg_";

	public static SysUser getCurrentUser() {
		if (curUser.get() != null) {
			SysUser user = (SysUser) curUser.get();
			return user;
		}
		SysUser sysUser = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			Authentication auth = securityContext.getAuthentication();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				if ((principal instanceof SysUser)) {
					sysUser = (SysUser) principal;
				}
			}
		}

		return sysUser;
	}

	public static Long getCurrentUserId() {
		SysUser curUser = getCurrentUser();
		if (curUser != null)
			return curUser.getId();
		return null;
	}

	public static void setCurrentUserAccount(String account) {
		SysUserService sysUserService = (SysUserService) AppUtil.getBean("sysUserService");
		SysUser sysUser = sysUserService.getByAccount(account);
		curUser.set(sysUser);
	}

	public static void setCurrentUser(SysUser sysUser) {
		curUser.set(sysUser);
	}

	public static void setCurrentOrg(Long orgId) {
		SysOrgService sysOrgService = (SysOrgService) AppUtil.getBean("sysOrgService");
		SysOrg sysOrg = sysOrgService.getById(orgId);
		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		HttpServletResponse response = RequestUtil.getHttpServletResponse();
		HttpSession session = request.getSession();
		saveSessionCookie(sysOrg, request, response, session);
	}

	public static void getCurrentOrgFromSession() {
		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		HttpServletResponse response = RequestUtil.getHttpServletResponse();
		HttpSession session = request.getSession();
		Long userId = getCurrentUserId();
		SysOrgService sysOrgService = (SysOrgService) AppUtil.getBean("sysOrgService");

		SysOrg sysOrg = (SysOrg) session.getAttribute("CurrentOrg_");
		if (sysOrg == null) {
			String currentOrgId = CookieUitl.getValueByName("CurrentOrg_", request);
			if (StringUtil.isEmpty(currentOrgId)) {
				sysOrg = sysOrgService.getDefaultOrgByUserId(userId);
				if (sysOrg != null) {
					CookieUitl.addCookie("CurrentOrg_", sysOrg.getId().toString(),true, request, response);
				}
			} else {
				Long orgId = Long.valueOf(Long.parseLong(currentOrgId));
				sysOrg = sysOrgService.getById(orgId);
			}
		}
		if (sysOrg != null) {
			saveSessionCookie(sysOrg, request, response, session);
			setCurrentOrg(sysOrg);
		}
	}

	public static SysOrg getCurrentOrg() {
		SysOrg sysOrg = (SysOrg) curOrg.get();
		if ((sysOrg == null) && (getCurrentUserId() != null)) {
			getCurrentOrgFromSession();
			sysOrg = (SysOrg) curOrg.get();
		}

		return sysOrg;
	}

	public static Long getCurrentOrgId() {
		SysOrg sysOrg = getCurrentOrg();
		if (sysOrg != null)
			return sysOrg.getId();
		return null;
	}

	public static String getCurrentUserSkin() {
		String skinStyle = "default";
		HttpServletRequest request = RequestUtil.getHttpServletRequest();
		HttpSession session = request.getSession();
		String skin = (String) session.getAttribute("skinStyle");
		if (StringUtil.isNotEmpty(skin))
			return skin;

		session.setAttribute("skinStyle", skinStyle);
		return skinStyle;
	}

	public static void setCurrentOrg(SysOrg sysOrg) {
		if (sysOrg == null)
			return;
		curOrg.set(sysOrg);

		ICache iCache = (ICache) AppUtil.getBean(ICache.class);
		Long userId = getCurrentUserId();
		if (userId == null)
			return;

		String userKey = "CurrentOrg_" + userId;
		iCache.add(userKey, sysOrg);
	}

	public static void cleanCurrentOrg() {
		curOrg.remove();
	}

	public static void cleanCurUser() {
		curUser.remove();
	}

	private static void saveSessionCookie(SysOrg sysOrg, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.setAttribute("CurrentOrg_", sysOrg);

		Long orgId = sysOrg.getId();

		CookieUitl.addCookie("CurrentOrg_", orgId.toString(),true, request, response);
	}

	public static void removeCurrentOrg(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("CurrentOrg_");
		}
		CookieUitl.delCookie("CurrentOrg_", request, response);
	}

	public static void clearAll() {
		curUser.remove();
		curOrg.remove();

		RequestUtil.clearHttpReqResponse();
		TaskThreadService.clearAll();
		TaskUserAssignService.clearAll();
		MessageUtil.clean();
	}

}
