package com.zuovx.book.service.user;

import com.zuovx.book.config.AuthJwt;
import com.zuovx.book.dao.UserMapper;
import com.zuovx.book.model.User;
import com.zuovx.book.model.UserExample;
import com.zuovx.book.service.bus.DealResult;
import com.zuovx.book.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zuoweixing
 * @date 2019-10-10 17:43
 */
@Service
@Slf4j
public class UserService {


	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthJwt authJwt;

	/**
	 * 注册
	 * @param user 用户信息
	 * @return 处理结果
	 */
	public DealResult register(User user){

		DealResult dealResult = new DealResult();
		if (user == null){
			dealResult.setSucceed(false);
			dealResult.setMsg("用户信息为空！");
			return dealResult;
		}
		UserExample userExample = new UserExample();
		userExample.createCriteria().andAccountEqualTo(user.getAccount())
				.andPhoneEqualTo(user.getPhone()).andIsDeletedEqualTo((byte) 0);
		List<User> userList = userMapper.selectByExample(userExample);
		if (userList != null && !userList.isEmpty()){
			dealResult.setSucceed(false);
			dealResult.setMsg("账号或手机号已存在！");
			return dealResult;
		}
		if (userMapper.insertSelective(user) == 1){
			log.info("账号：{} 注册成功 id:{}",user.getAccount(),user.getId());
			dealResult.setSucceed(true);
			dealResult.setMsg(sign(user.getAccount(),user.getId()));
		}else {
			dealResult.setSucceed(false);
			dealResult.setMsg("插入数据库失败");
		}
		return dealResult;
	}

	private String sign(String account,int userId){
		return TokenUtils.createJwt(account,String.valueOf(userId),null,null,null,authJwt.getTTLTimes(),authJwt.getBase64Secret());
	}


	/**
	 * 登陆
	 * @param user 用户信息
	 * @return 处理结果
	 */
	public DealResult login(User user){
		DealResult dealResult = new DealResult();
		dealResult.setSucceed(false);
		dealResult.setMsg("用户不合法！");
		if (user == null){
			dealResult.setMsg("用户信息为空！");
			dealResult.setSucceed(false);
			return dealResult;
		}
		UserExample userExample = new UserExample();
		userExample.createCriteria().andAccountEqualTo(user.getAccount())
			.andPasswordEqualTo(user.getPassword());
		List<User> userList = userMapper.selectByExample(userExample);
		if (userList != null && userList.size() == 1){
			dealResult.setSucceed(true);
			dealResult.setMsg(sign(userList.get(0).getAccount(),userList.get(0).getId()));
		}
		return dealResult;
	}


}

