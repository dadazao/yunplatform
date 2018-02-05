package com.cloudstong.platform.core.web.security;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CustomPwdEncoder implements PasswordEncoder {
    private static ThreadLocal<Boolean> ingorePwd = new ThreadLocal();

    public CustomPwdEncoder() {
    }

    public static void setIngore(boolean ingore) {
        ingorePwd.set(ingore);
    }

    public String encode(CharSequence rawPassword) {
        String pwd = rawPassword.toString();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(pwd.getBytes("UTF-8"));
            return new String(Base64.encodeBase64(digest));
        } catch (Exception var5) {
            var5.printStackTrace();
            return "";
        }
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (ingorePwd.get() != null && (Boolean)ingorePwd.get()) {
            return true;
        } else {
            String enc = this.encode(rawPassword);
            return enc.equals(encodedPassword);
        }
    }
}

