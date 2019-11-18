package com.b2en.springboot.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class TempVerManagementDto {

	private long tempMngmntId;

	private long custId;

	private Date lcnsIssuDtm;

	private Date lcnsExdt;

	private String lcnsKey;

	private String mgrId;

	private long tempLcnsId;

	private String etcInfo;
}
