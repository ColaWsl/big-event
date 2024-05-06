package com.wangsl.anno;

import com.wangsl.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StateValidation.class})
public @interface State {

	//校验失败的提示信息
	String message() default "state参数值限定 已发布 和 草稿";

	//指定分组
	Class<?>[] groups() default {};

	//获取到 @State 注解的附加信息
	Class<? extends Payload>[] payload() default {};

}
