package com.b2en.sms.dto;

import lombok.Data;

@Data
public class ContAndLcnsDto {
	
	// =============== Cont =================
	//@NotBlank(message="orgId가 빈칸입니다.")
	private int orgId;
	
	//@NotBlank(message="empId가 빈칸입니다.")
	private int empId;
	
	//@NotBlank(message="contDt가 빈칸입니다.")
	//@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-MM-dd의 형식으로 값이 입력되어야 합니다")
	private String contDt;
	
	//@NotBlank(message="contReportNo가 빈칸입니다.")
	private String contReportNo;
	
	//@NotBlank(message="contTpCd가 빈칸입니다.")
	private String contTpCd;
	
	//@NotBlank(message="installDt가 빈칸입니다.")
	//@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-MM-dd의 형식으로 값이 입력되어야 합니다")
	private String installDt;
	
	//@NotBlank(message="checkDt가 빈칸입니다.")
	//@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-MM-dd의 형식으로 값이 입력되어야 합니다")
	private String checkDt;
	
	//@NotBlank(message="mtncStartDt가 빈칸입니다.")
	//@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-MM-dd의 형식으로 값이 입력되어야 합니다")
	private String mtncStartDt;
	
	//@NotBlank(message="mtncEndDt가 빈칸입니다.")
	//@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-MM-dd의 형식으로 값이 입력되어야 합니다")
	private String mtncEndDt;
	
	// =============== ContDetail =================
	//private String[] contAmt;
	
	// =============== Lcns =================
	private LcnsDto[] lcns;
}
