package com.b2en.sms.dto;

import lombok.Data;

@Data
public class LcnsDto {
	
	private int prdtId;

	private String lcnsNo;
	
	private String lcnsIssuDt;
	
	private String certNo;
	
	private String lcnsStartDt;
	
	private String lcnsEndDt;
	
	private String scan;
}
