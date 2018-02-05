package com.cloudstong.platform.system.action;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.CookieUitl;
import com.cloudstong.platform.core.util.EncryptUtil;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.core.web.action.BaseAction;
import com.cloudstong.platform.resource.enterinfo.service.EnterpriseInfoService;
import com.cloudstong.platform.system.model.SubSystem;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.LogoService;
import com.cloudstong.platform.system.service.SysUserService;

/**
 * @author allan Created on 2014-10-14
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:登录Action
 */
@ParentPackage("default")
@Namespace("/")
@Results(value = { 
		@Result(name = "input", location = "/login.jsp"),
		@Result(name = "main", location = "/main.action", type="redirect")
})
@SuppressWarnings("serial")
public class LoginAction extends BaseAction {

	private String username;

	private String password;

	private String themeCode;

	@Resource
	private Properties configproperties;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private LogoService logoService;

	@Resource
	private EnterpriseInfoService enterpriseInfoService;

	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager;

	@Resource
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

	private String rememberPrivateKey = "ypPrivateKey";

	private static final String TRY_MAX_COUNT = "tryMaxCount";
	private static final int maxTryCount = 5;

	/**
	 * 登录
	 */
	@Action("login")
	public String login() throws Exception {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		HttpSession session = getSession();

		String validCodeEnabled = configproperties.getProperty("validCodeEnabled");

		boolean error = false;
		Integer tryCount;
		try {
			if ((validCodeEnabled != null) && ("true".equals(validCodeEnabled))) {
				String validCode = (String) getSession().getAttribute("com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY");
				String code = getRequest().getParameter("validCode");
				if ((validCode == null) || (StringUtil.isEmpty(code)) || (!validCode.equals(code))) {
					session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", "验证码不正确！");
					error = true;
					return "input";
				}
			}
			if ((StringUtil.isEmpty(username)) || (StringUtil.isEmpty(password))) {
				session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", "用户名密码为空!");
				error = true;
				return "input";
			}

			SysUser sysUser = sysUserService.getByAccount(username);
			String encrptPassword = EncryptUtil.encryptSha256(password);

			if ((sysUser != null) && (sysUser.getFromType() == 1)) {
				boolean authenticated = ldapUserAuthentication(username, password);
				if (!authenticated) {
					session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", "用户名密码输入错误!");
					error = true;
					return "input";
				}
				if (!encrptPassword.equals(sysUser.getPassword())) {
					sysUserService.updatePwd(sysUser.getId(), password);
				}

			} else if ((sysUser == null) || (!encrptPassword.equals(sysUser.getPassword()))) {
				session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", "用户名密码输入错误!");
				error = true;
				return "input";
			}

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			authRequest.setDetails(new WebAuthenticationDetails(getRequest()));
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication auth = authenticationManager.authenticate(authRequest);

			securityContext.setAuthentication(auth);
			
			session.setAttribute("user", sysUser);

			session.setAttribute("SPRING_SECURITY_LAST_USERNAME", username);

			session.removeAttribute("isAdmin");
			
			session.removeAttribute(SubSystem.CURRENT_SYSTEM);
			
			session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");

			sessionStrategy.onAuthentication(auth, request, response);

			AppContext.removeCurrentOrg(request, response);

			CookieUitl.delCookie("loginAction", request, response);

			writeRememberMeCookie(request, response, username, encrptPassword);
		}catch(DataAccessException e){
			session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", username + ":用户不存在!");
			error = true;
		}catch (LockedException e) {
			session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", username + ":用户被锁定!");
			error = true;
		} catch (DisabledException e) {
			session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", username + ":用户被禁用!");
			error = true;
		} catch (AccountExpiredException e) {
			session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", username + ":用户已过期!");
			error = true;
		} catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", "系统错误!");
			error = true;
		} finally {
			if (error) {
				tryCount = (Integer) session.getAttribute("tryMaxCount");
				if (tryCount == null) {
					session.setAttribute("tryMaxCount", Integer.valueOf(1));
				} else {
					tryCount.intValue();

					session.setAttribute("tryMaxCount", Integer.valueOf(tryCount.intValue() + 1));
				}
				return "input";
			}
		}

		return "main";
	}

	private void writeRememberMeCookie(HttpServletRequest request, HttpServletResponse response, String username, String enPassword) {
		String rememberMe = request.getParameter("_spring_security_remember_me");
		if ("1".equals(rememberMe)) {
			long tokenValiditySeconds = 1209600L;
			long tokenExpiryTime = System.currentTimeMillis() + tokenValiditySeconds * 1000L;
			String signatureValue = DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":" + enPassword + ":" + rememberPrivateKey);
			String tokenValue = username + ":" + tokenExpiryTime + ":" + signatureValue;
			String tokenValueBase64 = new String(Base64.encodeBase64(tokenValue.getBytes()));
			Cookie cookie = new Cookie("SPRING_SECURITY_REMEMBER_ME_COOKIE", tokenValueBase64);
			cookie.setMaxAge(157680000);
			cookie.setPath(org.springframework.util.StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
			response.addCookie(cookie);
		}
	}

	private boolean ldapUserAuthentication(String userId, String password) {
		return true;
	}

	/**
	 * Description:退出
	 */
	@Action("logout")
	public String logout() throws Exception {
		return NONE;
	}

	/**
	 * 
	 * Description: 获得用户IP
	 */
	private String getLoginUserIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getThemeCode() {
		return themeCode;
	}

	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}

}
