package com.b2en.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrgDto {
	
	private String orgId;
	
	private String orgNm;
	
	private String orgAddr;
}
