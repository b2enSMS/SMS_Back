package com.b2en.sms.dto.toclient;

import com.b2en.sms.dto.autocompleteinfo.B2enAC;
import com.b2en.sms.dto.autocompleteinfo.CustAC;

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
	
	private CustAC[] meetAttendCust;
	
	private B2enAC[] meetAttendEmp;
	
}
