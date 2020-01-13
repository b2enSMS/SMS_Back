package com.b2en.sms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class PrdtDto {
	
	@NotBlank(message="prdtNm이 빈칸입니다.")
	private String prdtNm;
	
	@NotBlank(message="prdtVer가 빈칸입니다.")
	private String prdtVer;
	
	@NotBlank(message="prdtDesc가 빈칸입니다.")
	private String prdtDesc;
	
	@NotBlank(message="prdtAmt가 빈칸입니다.")
	@Pattern(regexp="[0-9]+$", message="prdtAmt는 숫자만 입력되어야 합니다.")
	private String prdtAmt;
	
	@NotBlank(message="prdtTpCd가 빈칸입니다.")
	private String prdtTpCd;
}
