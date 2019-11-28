package com.b2en.springboot.dto;

import lombok.Data;

@Data
public class CustDtoToClient {
	
	private String custId;
	
	private OrgDtoToClient org;
	
	private String custNm;
	
	private String custRankNm;

	private String email;

	private String telNo;
	
}
