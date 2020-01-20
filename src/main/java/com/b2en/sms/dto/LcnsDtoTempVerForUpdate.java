package com.b2en.sms.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.b2en.sms.customvalidator.StartEndValid;

import lombok.Data;

@Data
@StartEndValid.List(value = {
	@StartEndValid(start="lcnsStartDt", end="lcnsEndDt", message="라이센스 시작일과 라이센스 종료일의 선후관계가 맞지 않습니다.") }
)
public class LcnsDtoTempVerForUpdate {
	
	private int lcnsId;
	
	@Min(value = 1, message="제품이 선택되지 않았습니다.")
	private int prdtId;
	
	//@NotBlank(message="제품명이 빈칸입니다.")
	//private String prdtNm;
	
	@NotBlank(message="라이센스 발행일이 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스 발행일은 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String lcnsIssuDt;
	
	@NotBlank(message="라이센스유형코드가 빈칸입니다.")
	private String lcnsTpCd;
	
	@NotBlank(message="라이센스개시일자가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스개시일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String lcnsStartDt;
	
	@NotBlank(message="라이센스종료일자가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스종료일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String lcnsEndDt;
}
