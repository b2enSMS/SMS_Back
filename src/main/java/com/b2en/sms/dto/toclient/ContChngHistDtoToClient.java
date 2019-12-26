package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class ContChngHistDtoToClient {
	
	private int contId;
	
	private int custId;
	
	private String custNm;
	
	private int orgId;
	
	private String orgNm;
	
	private int empId;
	
	private String empNm;
	
	private String contDt;
	
	private String contTotAmt;
	
	private String contReportNo;
	
	private String installDt;
	
	private String checkDt;
	
	private String mtncStartDt;
	
	private String mtncEndDt;
}
