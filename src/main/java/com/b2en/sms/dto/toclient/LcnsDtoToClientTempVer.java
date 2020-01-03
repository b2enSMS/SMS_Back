package com.b2en.sms.dto.toclient;

import java.sql.Date;

import lombok.Data;

@Data
public class LcnsDtoToClientTempVer {
	
	private int lcnsId;

	private int prdtId;
	
	private String prdtNm;

	private Date lcnsIssuDt;
	
	private String lcnsTpCd;
	
	private String lcnsTpNm;
	
	private Date lcnsStartDt;

	private Date lcnsEndDt;

}
