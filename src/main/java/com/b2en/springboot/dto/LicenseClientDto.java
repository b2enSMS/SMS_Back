package com.b2en.springboot.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class LicenseClientDto {

	private long lcnsId;

	private long solution;

	private String lcnsKey;

	private String lcnsNm;

	private String lcnsTp;

	private Date lcnsIssuDtm;

	private Date lcnsExdt;
}
