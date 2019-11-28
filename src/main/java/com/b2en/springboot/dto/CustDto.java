package com.b2en.springboot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustDto {
	
	private String custId;
	
	@NotBlank(message="고객사 정보가 빈칸입니다.")
	private String orgId;
	
	@NotBlank(message="고객명이 빈칸입니다.")
	@Size(min=2, max=6, message="고객명은 2자 이상, 6자 이하여야 합니다.")
	private String custNm;
	
	@NotBlank(message="직책이 빈칸입니다.")
	private String custRankNm;
	
	@Email(message="올바른 이메일 형식이 아닙니다.")
	private String email;
	
	@Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$", message="올바른 전화번호 형식이 아닙니다.")
	private String telNo;
	
	private String custTpCd;
}
