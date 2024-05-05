package com.wangsl.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wangsl.pojo.Result;
import com.wangsl.pojo.User;
import com.wangsl.service.UserService;
import com.wangsl.utils.JwtUtil;
import com.wangsl.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService){
		this.userService = userService;
	}

	@PostMapping("/register")
	public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
	                       @Pattern(regexp = "^\\S{5,16}$") String password){
		// 查询用户
		User user = userService.findByUsername(username);
		if(user == null){
			userService.register(username, password);
			return Result.success();
		}else {
			return Result.error("用户名已被占用");
		}
	}


	@PostMapping("/login")
	public Result login(@Pattern(regexp = "^\\S{5,16}$") String username,
	                    @Pattern(regexp = "^\\S{5,16}$") String password){
		// 查询用户
		User user = userService.findByUsername(username);
		// 校验用户名
		if(user == null)
			return Result.error("用户名不存在");
		// 校验密码
		if(user.getPassword().equals(Md5Util.getMD5String(password))){
			Map<String, Object> claims = new HashMap<>();
			claims.put("id", user.getId());
			claims.put("username", user.getUsername());
			String token = JwtUtil.genToken(claims);
			return Result.success(token);
		}
		return Result.error("密码错误");
	}
}
