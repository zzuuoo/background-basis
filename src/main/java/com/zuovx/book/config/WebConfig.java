package com.zuovx.book.config;

import com.zuovx.book.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zuoweixing
 * @date 2019-10-11 13:59
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
		registry.addInterceptor(authorityInterceptor()).addPathPatterns("/**");
	}

	/**
	 * 前后端分离，统一解决跨域问题
	 * 前端每次发送请求也都有在ajax里面设置xhrFields:{withCredentials: true}属性。
	 * @param registry r
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// 放行哪些原始域
				.allowedOrigins("*")
				.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
				.maxAge(3600)
				.allowedHeaders("*")
				.allowCredentials(true);
	}

	@Bean
	public LoginInterceptor authorityInterceptor() {
		return new LoginInterceptor();
	}
}