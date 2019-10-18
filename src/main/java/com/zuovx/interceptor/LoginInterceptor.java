package com.zuovx.interceptor;

import com.zuovx.annotation.LoginRequired;
import com.zuovx.config.AuthJwt;
import com.zuovx.dao.UserMapper;
import com.zuovx.utils.TokenUtils;
import com.zuovx.utils.Constants;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;

/**
 * 登陆验证拦截器
 * @author zuovx
 * @date 2019-10-11 13:57
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AuthJwt authJwt;

	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// ①:START 方法注解级拦截器
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// 判断接口是否需要登录
		LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
		// 有 @LoginRequired 注解，需要认证

		// 防止乱码
		response.setContentType("text/html;charset=UTF-8");
		if (methodAnnotation != null) {
			// 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等

			String token = request.getParameter(Constants.TOKEN);
			if (token == null || token.isEmpty()){
				for (Cookie cookie : request.getCookies()){
					if (Constants.TOKEN.equals(cookie.getName())){
						token = URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			}
			Claims claims = TokenUtils.parseJwt(token,authJwt.getBase64Secret());
			if (claims == null){
				response.setStatus(403);
				response.getWriter().append("请先登录！");
				return false;
			}
			String userId = claims.get("userId").toString();
			String account = claims.get("account").toString();
			if(!userMapper.checkUserExists(account,userId)){
				response.setStatus(403);
				response.getWriter().append("请先登录！");
				return false;
			}
			return true;
		}
		return true;
	}
}