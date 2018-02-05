package com.cloudstong.platform.core.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import com.cloudstong.platform.core.common.AppContext;
import com.cloudstong.platform.core.util.CookieUitl;

public class YpSwitchUserFilter extends SwitchUserFilter {
    public static final String SwitchAccount = "origSwitch";

    public YpSwitchUserFilter() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        if (this.requiresSwitchUser(request)) {
            this.setAccount(AppContext.getCurrentUser().getUsername(), request, response);
        } else if (this.requiresExitUser(request)) {
            this.removeAccount(request, response);
        }

        super.doFilter(req, res, chain);
    }

    private void setAccount(String account, HttpServletRequest req, HttpServletResponse res) {
        CookieUitl.addCookie("origSwitch", account, true, req, res);
    }

    private void removeAccount(HttpServletRequest req, HttpServletResponse res) {
        CookieUitl.delCookie("origSwitch", req, res);
    }

}
