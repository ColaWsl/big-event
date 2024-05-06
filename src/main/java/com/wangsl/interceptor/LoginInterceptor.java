package com.wangsl.interceptor;

import com.wangsl.utils.JwtUtil;
import com.wangsl.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("Authorization");
		try {
			Map<String, Object> claims = JwtUtil.parseToken(token);
			ThreadLocalUtil.set(claims);
			System.out.println("用户校验成功 存入 Threadlocal");
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
