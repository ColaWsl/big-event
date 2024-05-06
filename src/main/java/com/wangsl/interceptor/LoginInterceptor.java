package com.wangsl.interceptor;

import com.wangsl.utils.JwtUtil;
import com.wangsl.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("Authorization");

		try {
			// 验证token是否有效
			String redisToken = stringRedisTemplate.opsForValue().get(token);
			if(redisToken == null){
				throw new RuntimeException();
			}

			Map<String, Object> claims = JwtUtil.parseToken(token);
			ThreadLocalUtil.set(claims);
			System.out.println("用户校验成功 存入 Threadlocal " + claims.get("id"));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(401);
			return false;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		ThreadLocalUtil.remove();
	}
}
