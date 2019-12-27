package com.b2en.sms.dto.toclient;

import java.sql.Date;

import com.b2en.sms.service.file.FileList;

import lombok.Data;

@Data
public class LcnsDtoToClient {
	
	private int lcnsId;
	
	private int contSeq;

	private int prdtId;
	
	private String prdtNm;

	private String lcnsNo;

	private Date lcnsIssuDt;
	
	private String lcnsTpCd;
	
	private String lcnsTpNm;

	private String certNo;
	
	private Date lcnsStartDt;

	private Date lcnsEndDt;
	
	private FileList[] fileList;
	
	private String contAmt;
	
	private String contNote;

}
