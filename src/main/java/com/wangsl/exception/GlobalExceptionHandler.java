package com.wangsl.exception;

import com.wangsl.pojo.Result;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public Result ex(Exception e){
		e.printStackTrace();
		return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
	}

	@ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
	public Result constraintViolationException(Exception e){
		e.printStackTrace();
		return Result.error("参数格式错误");
	}

}
