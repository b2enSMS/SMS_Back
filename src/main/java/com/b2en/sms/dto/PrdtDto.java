package com.b2en.sms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PrdtDto {
	
	@NotBlank(message="제품명이 빈칸입니다.")
	private String prdtNm;
	
	//@NotBlank(message="제품버전이 빈칸입니다.")
	private String prdtVer;
	
	//@NotBlank(message="제품설명이 빈칸입니다.")
	private String prdtDesc;
	
	//@NotBlank(message="제품단가가 빈칸입니다.")
	//@Pattern(regexp="[0-9]+$", message="제품단가는 숫자만 입력되어야 합니다.")
	private String prdtAmt;
	
	@NotBlank(message="제품구분코드가 빈칸입니다.")
	private String prdtTpCd;
}
