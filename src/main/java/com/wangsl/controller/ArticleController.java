package com.wangsl.controller;

import com.wangsl.pojo.Result;
import com.wangsl.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/article")
public class ArticleController {

	@RequestMapping("/list")
	public Result list(@RequestHeader(name = "Authorization") String token, HttpServletResponse response){
		return Result.success("list article");
	}

}
