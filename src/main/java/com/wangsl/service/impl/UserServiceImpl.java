package com.wangsl.service.impl;

import com.wangsl.mapper.UserMapper;
import com.wangsl.pojo.User;
import com.wangsl.service.UserService;
import com.wangsl.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;

	@Autowired
	public UserServiceImpl(UserMapper userMapper){
		this.userMapper = userMapper;
	}

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	@Override
	public User findByUsername(String username) {
		return userMapper.selectByUsername(username);
	}

	/**
	 * 注册
	 * @param username
	 * @param password
	 */
	@Override
	public void register(String username, String password) {
		// 加密密码
		String md5String = Md5Util.getMD5String(password);
		// 注册
		userMapper.insert(username, md5String);
	}
}
