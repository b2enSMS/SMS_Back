package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class TempVerDtoToClient {
	
	private int tempVerId;
	
	private String orgNm;
	
	private String custNm;
	
	private String user;
	
	private String empNm;
	
	private String macAddr;
	
	private String requestDate;
	
	private String issueReason;
	
}
