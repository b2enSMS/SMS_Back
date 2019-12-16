package com.b2en.sms.dto;

import lombok.Data;

@Data
public class LcnsDto {
	
	//@Min(value = 1, message="prdtId는 {value}보다 크거나 같아야 합니다.")
	private int prdtId;

	//@NotBlank(message="lcnsNo가 빈칸입니다.")
	private String lcnsNo;
	
	//@NotBlank(message="lcnsIssuDt가 빈칸입니다.")
	//@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-MM-dd의 형식으로 값이 입력되어야 합니다")
	private String lcnsIssuDt;
	
	//@NotBlank(message="lcnsTpCd가 빈칸입니다.")
	private String lcnsTpCd;
	
	//@NotBlank(message="certNo가 빈칸입니다.")
	private String certNo;
	
	//@NotBlank(message="lcnsStartDt가 빈칸입니다.")
	//@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-MM-dd의 형식으로 값이 입력되어야 합니다")
	private String lcnsStartDt;
	
	//@NotBlank(message="lcnsEndDt가 빈칸입니다.")
	//@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-MM-dd의 형식으로 값이 입력되어야 합니다")
	private String lcnsEndDt;
	
	//@NotBlank(message="scan이 빈칸입니다.")
	private String scan;
	
	// 납품단가, contDetail 생성에 필요
	private String contAmt;
}
