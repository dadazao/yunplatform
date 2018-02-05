package com.cloudstong.platform.core.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.Base64;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.core.util.StringUtil;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.util.SecurityUtil;

public class SwaggerAuthFilter implements Filter {
	public SwaggerAuthFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String authorization = req.getHeader("Authorization");
        if (StringUtil.isNotEmpty(authorization)) {
            SysUser user = AppContext.getCurrentUser();
            if (user == null && authorization.matches("^Basic\\s{1}[\\w|=]*?$")) {
                String[] splits = authorization.split(" ");
                String accountPwd64 = splits[1];
                String accountPwd = Base64.getFromBase64(accountPwd64);
                String[] arys = accountPwd.split(":");
                if (BeanUtils.isNotEmpty(arys) && arys.length == 2) {
                    SecurityUtil.login(req, arys[0], arys[1], false);
                }
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}
