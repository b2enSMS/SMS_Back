package com.b2en.sms.dto;

import lombok.Data;

@Data
public class ContAndLcnsDtoToClient {

	private int contId;
	
	private int orgId;
	
	private int empId;
	
	private String contDt;
	
	private String contReportNo;
	
	private String contTpCd;
	
	private String installDt;
	
	private String checkDt;
	
	private String mtncStartDt;
	
	private String mtncEndDt;
	
	private LcnsDtoToClient[] lcns;
}
