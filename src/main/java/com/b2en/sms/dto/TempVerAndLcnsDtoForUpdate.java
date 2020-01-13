package com.b2en.sms.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class TempVerAndLcnsDtoForUpdate {
	
	@Min(value = 1, message="기관 담당자가 선택되지 않았습니다.")
	private int custId;

	@Min(value = 1, message="담당자가 선택되지 않았습니다.")
	private int empId;
	
	@NotBlank(message="사용자가 빈칸입니다.")
	private String user;
	
	@NotBlank(message="MAC주소가 빈칸입니다.")
	private String macAddr;
	
	@NotBlank(message="요청일이 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]))$", message="요청일은 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String requestDate;
	
	private String issueReason;
	
	@Valid
	private LcnsDtoTempVerForUpdate[] lcns;
}
