package com.b2en.sms.dto;

import lombok.Data;

@Data
public class LcnsDtoTempVerForUpdate {
	
	private int lcnsId;
	
	private int prdtId;
	
	private String prdtNm;
	
	private String lcnsIssuDt;
	
	private String lcnsTpCd;
	
	private String lcnsStartDt;
	
	private String lcnsEndDt;
}
