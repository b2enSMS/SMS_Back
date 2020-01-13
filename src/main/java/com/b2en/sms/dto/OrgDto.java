package com.b2en.sms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class OrgDto {
	
	@NotBlank(message="orgNm이 빈칸입니다.")
	private String orgNm;
	
	@NotBlank(message="orgAddr이 빈칸입니다.")
	private String orgAddr;
}
