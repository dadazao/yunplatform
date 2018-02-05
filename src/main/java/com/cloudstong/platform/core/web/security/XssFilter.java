package com.cloudstong.platform.core.web.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.cloudstong.platform.core.model.ResultMessage;
import com.cloudstong.platform.core.util.StringUtil;

public class XssFilter implements Filter {
    private Pattern regex = Pattern.compile("<(\\S*?)[^>]*>.*?</\\1>|<[^>]+>", 106);
    @Resource(
        name = "xssUrl"
    )
    RegMatchers matchers;

    public XssFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        boolean isIngoreUrl = this.matchers.isContainUrl(req.getRequestURI());
        if (isIngoreUrl) {
            chain.doFilter(request, response);
        } else {
            boolean hasXss = this.checkXss(req);
            if (hasXss) {
                String reqWith = req.getHeader("x-requested-with");
                if (StringUtil.isEmpty(reqWith)) {
                    request.getRequestDispatcher("/commons/xss.jsp").forward(request, response);
                } else {
                    ResultMessage resultMessage = new ResultMessage(0, "检测到XSS攻击，请检是否输入了HTML字符！");
                    response.getWriter().print(resultMessage);
                }
            } else {
                chain.doFilter(request, response);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {
    }

    private boolean checkXss(HttpServletRequest request) {
        Enumeration params = request.getParameterNames();

        while(params.hasMoreElements()) {
            String key = params.nextElement().toString();
            String[] vals = request.getParameterValues(key);
            String val = StringUtil.join(vals, "");
            if (!StringUtil.isEmpty(val)) {
                Matcher regexMatcher = this.regex.matcher(val);
                if (regexMatcher.find()) {
                    return true;
                }
            }
        }

        return false;
    }

}
