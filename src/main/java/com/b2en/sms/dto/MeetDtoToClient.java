package com.b2en.sms.dto;

import lombok.Data;

@Data
public class MeetDtoToClient {

	private String meetId;

	private OrgDtoToClient org;

	private String meetDt;
	
	private String meetCnt;

	private String meetStartTime;
	
	private String meetTotTime;
	
}