package com.zuovx.service;

import com.zuovx.annotation.ServiceExceptionLog;
import com.zuovx.dao.UserMapper;
import com.zuovx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zuoweixing
 * @date 2019-10-10 15:55
 */
@Service
public class DemoService {

	@Autowired
	private UserMapper userMapper;

	public List<User> getAllUser(){
		return userMapper.selectByExample(null);
	}

	public List<User> getUserByName(String name){
		return userMapper.selectByName(name);
	}

	@ServiceExceptionLog(describe = "窝窝头")
	public String testException() throws Exception{
		throw new Exception("窝窝头一块钱四个，嘿嘿！");
	}
}
