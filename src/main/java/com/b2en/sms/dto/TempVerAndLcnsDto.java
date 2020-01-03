package com.b2en.sms.dto;

import lombok.Data;

@Data
public class TempVerAndLcnsDto {
	
	private int custId;

	private int empId;
	
	private String macAddr;
	
	private String requestDate;
	
	private String issueReason;
	
	private LcnsDtoTempVer[] lcns;

}
