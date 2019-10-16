package com.zuovx.book.controller;

import com.zuovx.book.annotation.LoginRequired;
import com.zuovx.book.model.User;
import com.zuovx.book.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zuoweixing
 * @date 2019-10-10 15:57
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private DemoService demoService;

	@RequestMapping("/getAllUser")
	@LoginRequired
	public List<User> getAllUser(){
		return demoService.getAllUser();
	}

	@RequestMapping(value = "/getUserByName",method = RequestMethod.GET)
	public List<User> getUserByName(@RequestParam(value = "name") String name){
		return demoService.getUserByName(name);
	}
}
