package com.powerboot.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * @description Created on 2020/12/13.
 */
public class ServletUtil {
    public static ApplicationContext getWebApplicationContext() {
        return WebApplicationContextUtils.findWebApplicationContext(getServletContext());
    }

    public static ServletContext getServletContext() {
        return getHttpServletRequest().getServletContext();
    }

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ra == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        }
        return ra.getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ra == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        }
        return ra.getResponse();
    }

    public static Optional<Cookie> getCookie(String cookieName) {
        return getCookie(getHttpServletRequest(), cookieName);
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String cookieName) {
        if (request == null) {
            return Optional.empty();
        }
        if (StringUtils.isBlank(cookieName)) {
            return Optional.empty();
        }
        cookieName = cookieName.trim();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        String finalCookieName = cookieName;
        return Arrays.stream(cookies).filter(it -> StringUtils.equals(finalCookieName, it.getName())).findFirst();
    }

    public static boolean setCookie(Cookie cookie) {
        if (cookie == null) {
            return false;
        }
        return setCookie(getHttpServletResponse(), cookie);
    }

    public static boolean setCookie(HttpServletResponse response, Cookie cookie) {
        if (response == null) {
            return false;
        }
        if (cookie == null) {
            return false;
        }
        response.addCookie(cookie);
        return true;
    }
}
