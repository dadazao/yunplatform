package com.cloudstong.platform.core.web.security;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cloudstong.platform.core.util.BeanUtils;

public class RegMatchers {
	private List<Pattern> ingoreUrls = new ArrayList();

    public RegMatchers() {
    }

    public void setIngoreUrls(List<String> urls) {
        if (!BeanUtils.isEmpty(urls)) {
            Iterator var3 = urls.iterator();

            while(var3.hasNext()) {
                String url = (String)var3.next();
                Pattern regex = Pattern.compile(url, 106);
                this.ingoreUrls.add(regex);
            }

        }
    }

    public boolean isContainUrl(String requestUrl) {
        Iterator var3 = this.ingoreUrls.iterator();

        while(var3.hasNext()) {
            Pattern pattern = (Pattern)var3.next();
            Matcher regexMatcher = pattern.matcher(requestUrl);
            if (regexMatcher.find()) {
                return true;
            }
        }

        return false;
    }
}
