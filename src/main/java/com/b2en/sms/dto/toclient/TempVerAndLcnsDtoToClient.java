package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class TempVerAndLcnsDtoToClient {
	
	private int tempVerId;
	
	private int custId;
	
	private String CustNm;

	private LcnsDtoToClientTempVer lcns;

	private int empId;
	
	private String empNm;
	
	private String macAddr;
	
}
