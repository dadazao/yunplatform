package com.cloudstong.platform.core.web.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.cloudstong.platform.core.model.ResultMessage;

import net.sf.json.JSONObject;

public class MultipleAuthenticationLoginEntry implements AuthenticationEntryPoint {
	private String defaultLoginUrl = "/login.jsp";
    private List<DirectUrlResolver> directUrlResolvers = new ArrayList();

    public MultipleAuthenticationLoginEntry() {
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setCharacterEncoding("UTF-8");
            ResultMessage resultMessage = new ResultMessage(2, "登陆超时!");
            response.getWriter().print(JSONObject.fromObject(resultMessage).toString());
        } else {
            String ctxPath = request.getContextPath();
            Iterator var7 = this.directUrlResolvers.iterator();

            while(var7.hasNext()) {
                DirectUrlResolver directUrlResolver = (DirectUrlResolver)var7.next();
                if (directUrlResolver.support(request)) {
                    String loginUrl = directUrlResolver.directUrl();
                    response.sendRedirect(ctxPath + loginUrl);
                    return;
                }
            }

            response.sendRedirect(ctxPath + this.defaultLoginUrl);
        }
    }

    public void setDefaultLoginUrl(String defaultLoginUrl) {
        this.defaultLoginUrl = defaultLoginUrl;
    }

    public void setDirectUrlResolvers(List<DirectUrlResolver> directUrlResolvers) {
        this.directUrlResolvers = directUrlResolvers;
    }
}