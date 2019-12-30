package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class CustDtoToClientExpanded {
	
	private int custId;
	
	private int orgId;
	
	private String orgNm;
	
	private String custNm;
	
	private String custRankNm;

	private String email;

	private String telNo;

	private String custTpCd;

	private String custTpCdNm;
}
