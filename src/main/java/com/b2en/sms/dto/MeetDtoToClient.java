package com.b2en.sms.dto;

import lombok.Data;

@Data
public class MeetDtoToClient {

	private int meetId;

	private int orgId;
	
	private String orgNm;

	private String meetDt;
	
	private String meetCnt;

	private String meetStartTime;
	
	private String meetTotTime;
	
	private String meetTpCd;
	
}
