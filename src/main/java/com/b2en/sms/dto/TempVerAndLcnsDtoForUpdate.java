package com.b2en.sms.dto;

import lombok.Data;

@Data
public class TempVerAndLcnsDtoForUpdate {
	
	private int custId;

	private int empId;
	
	private String macAddr;
	
	private String requestDate;
	
	private String issueReason;
	
	private LcnsDtoTempVerForUpdate[] lcns;
}
