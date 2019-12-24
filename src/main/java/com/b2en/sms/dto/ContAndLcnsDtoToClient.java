package com.b2en.sms.dto;

import lombok.Data;

@Data
public class ContAndLcnsDtoToClient {

	private int contId;
	
	private int custId;
	
	private String custNm;
	
	private int orgId;
	
	private String orgNm;
	
	private int empId;
	
	private String empNm;
	
	private int headContId;
	
	private String contNm;
	
	private String contDt;
	
	private String contReportNo;
	
	private String contTpCd;
	
	private String contTpNm;
	
	private String installDt;
	
	private String checkDt;
	
	private String mtncStartDt;
	
	private String mtncEndDt;
	
	private LcnsDtoToClient[] lcns;
}
