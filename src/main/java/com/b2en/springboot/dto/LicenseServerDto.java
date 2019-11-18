package com.b2en.springboot.dto;

import java.sql.Date;

import com.b2en.springboot.entity.Solution;

import lombok.Data;

@Data
public class LicenseServerDto {
	
	private long lcnsId;

	private Solution solution;

	private String lcnsKey;

	private String lcnsNm;

	private String lcnsTp;

	private Date lcnsIssuDtm;

	private Date lcnsExdt;
}
