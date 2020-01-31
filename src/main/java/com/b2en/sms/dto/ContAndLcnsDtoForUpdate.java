package com.b2en.sms.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.b2en.sms.customvalidator.StartEndValid;

import lombok.Data;

@Data
@StartEndValid.List(value = {
	@StartEndValid(start = "mtncStartDt", end = "mtncEndDt", message = "유지보수 시작일과 유지보수 종료일의 선후관계가 맞지 않습니다.") }
)
public class ContAndLcnsDtoForUpdate {
	
	// =============== Cont =================
	private int custId;
		
	@Min(value = 1, message="기관명이 누락되었습니다.")
	private int orgId;
	
	@Min(value = 1, message="담당자명이 누락되었습니다.")
	private int empId;
	
	private int headContId;
	
	@NotBlank(message="사업명이 빈칸입니다.")
	private String contNm;

	@NotBlank(message="계약일자가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="계약일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String contDt;

	@NotBlank(message="수주보고서번호가 빈칸입니다.")
	private String contReportNo;
	
	@NotBlank(message="계약유형코드가 빈칸입니다.")
	private String contTpCd;
	
	@NotBlank(message="설치일자가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="설치일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String installDt;
	
	@NotBlank(message="검수일자가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="검수일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String checkDt;
	
	@NotBlank(message="유지보수개시일이 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="유지보수개시일은 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String mtncStartDt;
	
	@NotBlank(message="유지보수종료일이 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="유지보수종료일은 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String mtncEndDt;

	// =============== Lcns =================
	@Valid
	private LcnsDtoNew.Request[] lcns;
	
}
