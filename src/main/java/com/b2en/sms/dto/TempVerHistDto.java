package com.b2en.sms.dto;

import lombok.Data;

@Data
public class TempVerHistDto {
	
	private int tempVerHistSeq;
	
	private String orgNm;
	
	private String custNm;
	
	private String empNm;
	
	private String macAddr;
	
	private String requestDate;
	
	private String issueReason;
}
