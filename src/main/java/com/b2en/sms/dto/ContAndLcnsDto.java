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
public class ContAndLcnsDto {
	
	// =============== Cont =================
	private int custId;
	
	@Min(value = 1, message="orgId는 {value}보다 크거나 같아야 합니다.")
	private int orgId;
	
	@Min(value = 1, message="empId는 {value}보다 크거나 같아야 합니다.")
	private int empId;
	
	private int headContId;
	
	private String contNm;
	
	@NotBlank(message="contDt가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String contDt;
	
	@NotBlank(message="contReportNo가 빈칸입니다.")
	private String contReportNo;
	
	@NotBlank(message="contTpCd가 빈칸입니다.")
	private String contTpCd;
	
	@NotBlank(message="installDt가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String installDt;
	
	@NotBlank(message="checkDt가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String checkDt;
	
	@NotBlank(message="mtncStartDt가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String mtncStartDt;
	
	@NotBlank(message="mtncEndDt가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String mtncEndDt;
	
	// =============== Lcns =================
	@Valid
	private LcnsDto[] lcns;
}
