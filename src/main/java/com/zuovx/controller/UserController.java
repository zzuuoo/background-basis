package com.zuovx.controller;

import com.alibaba.fastjson.JSON;
import com.zuovx.annotation.LoginRequired;
import com.zuovx.config.AuthJwt;
import com.zuovx.model.User;
import com.zuovx.service.bus.DealResult;
import com.zuovx.service.bus.JsonResult;
import com.zuovx.service.user.UserService;
import com.zuovx.utils.Constants;
import com.zuovx.utils.CookieUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @author zuoweixing
 * @date 2019-10-11 14:27
 */
@Api(value = "UserController",description = "用户相关接口")
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthJwt authJwt;

	/**
	 * 注册，成功后生成token返回到cookie中
	 * @return
	 */
	@ApiOperation(value = "注册",httpMethod = "POST",response = JsonResult.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful — 请求已完成"),
			@ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
			@ApiResponse(code = 401, message = "未授权客户机访问数据"),
			@ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
			@ApiResponse(code = 500, message = "服务器不能完成请求")}
	)
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public JsonResult register(HttpServletResponse response, @RequestBody String requestBody){
		JsonResult jsonResult = new JsonResult();
		try {
			User user = JSON.parseObject(requestBody,User.class);
			DealResult dealResult = userService.register(user);
			if (dealResult.isSucceed()){
				jsonResult.setStatus(200);
				Cookie cookie = new Cookie(Constants.TOKEN,URLEncoder.encode(dealResult.getMsg(),"utf-8"));
				cookie.setPath("/");
				response.addCookie(cookie);
				jsonResult.setMsg("注册成功");
			}else {
				jsonResult.setMsg(dealResult.getMsg());
				jsonResult.setStatus(400);
			}
		}catch (Exception e){
			jsonResult.setMsg(e.getMessage());
			jsonResult.setStatus(400);
			log.info(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 登陆
	 * @return
	 */
	@ApiOperation("登陆")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "requestBody",value = "{\"account\":\"123456\",\"password\":\"root123456\"}", required = true),
	})
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public JsonResult login(HttpServletResponse response, @RequestBody String requestBody){
		JsonResult jsonResult = new JsonResult();
		try {
			User user = JSON.parseObject(requestBody,User.class);
			DealResult dealResult = userService.login(user);
			if (dealResult.isSucceed()){
				jsonResult.setStatus(200);
				Cookie cookie = new Cookie(Constants.TOKEN, URLEncoder.encode(dealResult.getMsg(),"utf-8"));
				cookie.setPath("/");
				response.addCookie(cookie);
				jsonResult.setMsg("登陆成功");
			}else {
				jsonResult.setMsg(dealResult.getMsg());
				jsonResult.setStatus(400);
			}
		}catch (Exception e){
			jsonResult.setMsg(e.getMessage());
			jsonResult.setStatus(400);
			log.info(e.getMessage());
		}
		return jsonResult;
	}

	/**
	 * 登出
	 * @param response r
	 * @param request r
	 * @return r
	 */
	@ApiOperation("登出")
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	@LoginRequired
	public JsonResult logout(HttpServletResponse response,HttpServletRequest request){
		JsonResult jsonResult = new JsonResult();
		jsonResult.setStatus(200);
		Cookie cookie = CookieUtils.get(request,Constants.TOKEN);
		if (cookie != null){
			CookieUtils.setResponse(response,cookie.getName(),null);
		}
		return jsonResult;
	}
}
