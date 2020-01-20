package com.b2en.sms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginInfo {

	@NotBlank(message="이메일을 입력하지 않았습니다.")
	@Email(message="올바른 이메일의 형식이 아닙니다.")
	private String email;
	
	private String username;
	
	@NotBlank(message="비밀번호를 입력하지 않았습니다.")
	private String password;
	
}
