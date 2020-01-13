package com.b2en.sms.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class TempVerAndLcnsDto {
	
	@Min(value = 1, message="custId는 {value}보다 크거나 같아야 합니다.")
	private int custId;

	@Min(value = 1, message="empId는 {value}보다 크거나 같아야 합니다.")
	private int empId;
	
	@NotBlank(message="lcnsNo가 빈칸입니다.")
	private String user;
	
	@NotBlank(message="lcnsNo가 빈칸입니다.")
	private String macAddr;
	
	@NotBlank(message="requestDate가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String requestDate;
	
	private String issueReason;
	
	@Valid
	private LcnsDtoTempVer[] lcns;

}
