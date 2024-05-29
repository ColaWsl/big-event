package com.wangsl.controller;

import com.wangsl.pojo.Result;
import com.wangsl.pojo.User;
import com.wangsl.pojo.vo.UpdatePwdVo;
import com.wangsl.service.UserService;
import com.wangsl.utils.JwtUtil;
import com.wangsl.utils.Md5Util;
import com.wangsl.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	private final StringRedisTemplate stringRedisTemplate;

	@Autowired
	public UserController(UserService userService, StringRedisTemplate stringRedisTemplate){
		this.userService = userService;
		this.stringRedisTemplate = stringRedisTemplate;
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
			// token 存入 redis 并设置与JWT相同的过期时间 12h
			stringRedisTemplate.opsForValue().set(token, token, 12, TimeUnit.HOURS);
			return Result.success(token);
		}
		return Result.error("密码错误");
	}

	// 登出
	@GetMapping("/logout")
	public Result logout(@RequestHeader("Authorization") String authorization){
		// 从redis中删除当前token 即使JWT未过期但redis中不存在也无法通过校验
		stringRedisTemplate.delete(authorization);
		return Result.success();
	}

	@GetMapping("/userInfo")
	public Result userInfo(){
		Map<String, Object> map = ThreadLocalUtil.get();
		String username = (String) map.get("username");
		User user = userService.findByUsername(username);
		return Result.success(user);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Validated User user){
		userService.update(user);
		return Result.success();
	}

	@PatchMapping("/updateAvatar")
	public Result updateAvatar(@RequestParam @URL String avatarUrl){
		userService.updateAvatar(avatarUrl);
		return Result.success();
	}

	@PatchMapping("/updatePwd")
	public Result updatePwd(@RequestBody @Validated UpdatePwdVo updatePwdVo, @RequestHeader("Authorization") String token){
		// 校验参数
		if(!updatePwdVo.getNew_pwd().equals(updatePwdVo.getRe_pwd()))
			return Result.error("新密码输入不一致");

		Map<String, Object> map = ThreadLocalUtil.get();
		String username = (String) map.get("username");
		// 验证旧密码
		User user = userService.findByUsername(username);
		if(!user.getPassword().equals(Md5Util.getMD5String(updatePwdVo.getOld_pwd())))
			return Result.error("密码错误");
		// 更新密码
		userService.updatePassword(updatePwdVo.getNew_pwd());
		// 删除redis 中的token
		stringRedisTemplate.delete(token);
		return Result.success();
	}

}
