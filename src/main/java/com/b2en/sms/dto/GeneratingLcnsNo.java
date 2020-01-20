package com.b2en.sms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class GeneratingLcnsNo {
	
	@NotBlank()
	private String prdtNm;
	
	@NotBlank()
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$")
	private String installDt;
}
