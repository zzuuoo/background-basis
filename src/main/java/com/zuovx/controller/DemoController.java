package com.zuovx.controller;

import com.zuovx.annotation.LoginRequired;
import com.zuovx.annotation.OperatorLogController;
import com.zuovx.model.User;
import com.zuovx.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zuovx
 * @date 2019-10-10 15:57
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

	@Autowired
	private DemoService demoService;

	@RequestMapping("/getAllUser")
	@LoginRequired
	public List<User> getAllUser(){
		return demoService.getAllUser();
	}

	@RequestMapping(value = "/getUserByName",method = RequestMethod.GET)
	@OperatorLogController
	public List<User> getUserByName(@RequestParam(value = "name") String name){
		return demoService.getUserByName(name);
	}
	@RequestMapping("/test")
	public String testServiceError(){
		try {
			return demoService.testException();
		} catch (Exception e) {
			log.error("error:",e);
		}
		return "嘿嘿！";
	}
}
