package com.b2en.sms.dto;

import com.b2en.sms.service.file.FileList;

import lombok.Data;

@Data
public class LcnsDtoForUpdate {
	
	private int lcnsId;
	
	private int contSeq;

	private int prdtId;
	
	private String prdtNm;

	private String lcnsNo;

	private String lcnsIssuDt;
	
	private String lcnsTpCd;
	
	private String lcnsTpNm;

	private String certNo;
	
	private String lcnsStartDt;

	private String lcnsEndDt;
	
	private FileList[] fileList;
	
	private String contAmt;
	
	private String contNote;

}
