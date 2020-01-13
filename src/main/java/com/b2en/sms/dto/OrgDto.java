package com.b2en.sms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class OrgDto {
	
	@NotBlank(message="고객사명이 빈칸입니다.")
	private String orgNm;
	
	@NotBlank(message="고객사주소가 빈칸입니다.")
	private String orgAddr;
}
