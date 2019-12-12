package com.b2en.sms.dto;

import lombok.Data;

@Data
public class CmmnDetailCdDtoToClient {
	
	// 공통상세코드
	private String cmmnDetailCd;
	
	// 공통코드
	private String cmmnCd;
	
	// 공통상세코드명
	private String cmmnDetailCdNm;
	
	// 공통상세코드설명
	private String cmmnDetailCdDesc;
}
