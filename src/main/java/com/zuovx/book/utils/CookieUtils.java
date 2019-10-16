package com.zuovx.book.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zuoweixing
 * @date 2019-10-11 16:08
 */
public class CookieUtils {
	public static Cookie get(HttpServletRequest request,
							 String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		return cookieMap.getOrDefault(name, null);
	}

	public static void setRequest(HttpServletRequest request,String name,String value){
	}
	public static void setResponse(HttpServletResponse response, String name,String value){
		Cookie cookie = new Cookie(name,value);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 将cookie封装成Map
	 * @param request r
	 * @return r
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie: cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

}
