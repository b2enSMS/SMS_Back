package com.b2en.springboot.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class MaintenanceDto {

	private long mntcId;
	
	private long mgrId;

	private long contId;

	private String mntcInfo;

	private Date mntcDtm;

	private String mntcTp;
}
