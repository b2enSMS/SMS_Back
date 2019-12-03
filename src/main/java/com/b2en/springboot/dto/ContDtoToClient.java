package com.b2en.springboot.dto;

import lombok.Data;

@Data
public class ContDtoToClient {

	private String contId;
	
	//private OrgDtoToClient org;
	private String orgId;
	
	private String orgNm;
	
	//private B2enDtoToClient b2en;
	private String empId;
	
	private String empNm;
	
	private String contDt;
	
	private String contTotAmt;
	
	private String contReportNo;
	
	private String installDt;
	
	private String checkDt;
	
	private String mtncStartDt;
	
	private String mtncEndDt;
}
