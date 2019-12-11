package com.b2en.sms.dto;

import lombok.Data;

@Data
public class ContDto {
	
	private int orgId;
	
	private int empId;
	
	private String contDt;
	
	private String contReportNo;
	
	private String installDt;
	
	private String checkDt;
	
	private String mtncStartDt;
	
	private String mtncEndDt;
	
	// 라이센스ID, contDetail 생성에 필요
	private int[] lcnsId;
	
	// 계약금액(계약 마다 제품 금액이 달라질 수 있음), contDetail 생성에 필요
	private String[] contAmt;
}
