package com.b2en.sms.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class LcnsDtoTempVerForUpdate {
	
	private int lcnsId;
	
	private int prdtId;
	
	private String prdtNm;
	
	private Date lcnsIssuDt;
	
	private String lcnsTpCd;
	
	private Date lcnsStartDt;
	
	private Date lcnsEndDt;
}
