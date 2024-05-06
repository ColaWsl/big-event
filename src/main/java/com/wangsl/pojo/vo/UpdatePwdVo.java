package com.wangsl.pojo.vo;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdatePwdVo {
	@Pattern(regexp = "^\\S{5,16}$")
	private String old_pwd;
	@Pattern(regexp = "^\\S{5,16}$")
	private String new_pwd;
	@Pattern(regexp = "^\\S{5,16}$")
	private String re_pwd;
}
