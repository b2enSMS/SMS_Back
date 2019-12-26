package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class ContDtoToClient {

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
	
	private String contTotAmt;
	
	private String contReportNo;
	
	private String contTpCd;
	
	private String installDt;
	
	private String checkDt;
	
	private String mtncStartDt;
	
	private String mtncEndDt;
	
	// 유지보수종료일까지 30일 이내일 경우 true, 아니면 false
	private boolean tight;

	public void setTight(boolean tight) {
		this.tight = tight;
	}
}
