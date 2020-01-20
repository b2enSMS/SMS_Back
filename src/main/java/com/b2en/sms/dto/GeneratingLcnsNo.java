package com.b2en.sms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class GeneratingLcnsNo {
	
	@NotBlank(message="제품이 선택되지 않았습니다.")
	private String prdtNm;
	
	@NotBlank(message="설치일자가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="미팅일시는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String installDt;
}
