package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class MeetAndAttendDtoToClient {
	
	private int meetId;

	private String meetDt;
	
	private String meetCnt;

	private String meetStartTime;
	
	private String meetTotTime;
	
	private String meetTpCd;
	
	private String meetTpCdNm;
	
	private int[] custId;
	
	private int[] empId;
	
}
