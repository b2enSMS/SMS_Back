package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class TempVerDtoToClient {
	
	private int tempVerId;
	
	private int custId;
	
	private String CustNm;

	private int lcnsId;
	
	private String lcnsNo;

	private int empId;
	
	private String empNm;
	
	private String macAddr;
}
