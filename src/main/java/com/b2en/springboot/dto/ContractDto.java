package com.b2en.springboot.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ContractDto {
	
	private long contId;
	
	private long lcnsId;

	private long custId;

	private Date mntcIssuDtm;

	private Date mntcExdt;

	private Date contDtm;
}
