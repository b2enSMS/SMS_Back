package com.b2en.sms.dto;

import lombok.Data;

@Data
public class CustDtoToClient {
	
	private int custId;
	
	private int orgId;
	
	private String orgNm;
	
	private String custNm;
	
	private String custRankNm;

	private String email;

	private String telNo;
	
}
