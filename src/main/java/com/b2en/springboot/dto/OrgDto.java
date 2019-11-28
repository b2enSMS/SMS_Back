package com.b2en.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrgDto {
	
	private String orgId;
	
	@NotBlank(message="고객사명이 빈칸입니다.")
	@Size(min=2, max=20, message="고객사명은 2자 이상, 20자 이하여야합니다.")
	private String orgNm;
	
	@NotBlank(message="주소가 빈칸입니다.")
	private String orgAddr;
}
