package com.b2en.sms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class B2enDto {
	
	@NotBlank(message="담당자명이 빈칸입니다.")
	@Pattern(regexp="[가-힣|a-zA-Z]+$", message="담당자명은 한글 또는 영문만 입력되어야 합니다.")
	private String empNm;
	
	//@NotBlank(message="사번이 빈칸입니다.")
	private String empNo;
	
	//@NotBlank(message="이메일이 빈칸입니다.")
	@Email(message="올바흔 이메일의 형식이 아닙니다.")
	private String email;
	
	//@NotBlank(message="전화번호가 빈칸입니다.")
	@Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$", message="올바른 전화번호 형식이 아닙니다.")
	private String telNo;
	
	@NotBlank(message="역할구분코드가 빈칸입니다.")
	private String empTpCd;
	
}
