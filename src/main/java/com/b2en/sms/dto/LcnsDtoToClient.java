package com.b2en.sms.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class LcnsDtoToClient {
	private int lcnsId;

	private int prdtId;
	
	private String prdtNm;

	private String lcnsNo;

	private Date lcnsIssuDt;

	private String certNo;
	
	private Date lcnsStartDt;

	private Date lcnsEndDt;
	
	private String scan;
}
