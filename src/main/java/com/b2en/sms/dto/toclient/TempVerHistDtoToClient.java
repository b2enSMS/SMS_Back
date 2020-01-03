package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class TempVerHistDtoToClient {
	
	private int tempVerHistSeq;
	
	private String orgNm;
	
	private String custNm;
	
	private String empNm;
	
	private String macAddr;
	
	private String requestDate;
	
	private String issueReason;
}
