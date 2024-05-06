package com.wangsl.validation;

import com.wangsl.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

	// 校验规则
	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if(value == null)
			return false;
		return value.equals("已发布") || value.equals("草稿");
	}
}
