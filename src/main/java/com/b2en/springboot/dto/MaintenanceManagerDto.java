package com.b2en.springboot.dto;

import lombok.Data;

@Data
public class MaintenanceManagerDto {
	
	private long mgrId;

	private String mgrNm;

	private String email;

	private String telNo;
}
