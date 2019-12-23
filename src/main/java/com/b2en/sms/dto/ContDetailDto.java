package com.b2en.sms.dto;

import javax.persistence.Column;

import lombok.Data;

@Data
public class ContDetailDto {

	// 라이센스ID (FK)
	//@NotBlank(message="lcnsId가 빈칸입니다.")
	private int lcnsId;

	// 계약금액
	//@NotBlank(message="contAmt가 빈칸입니다.")
	//@Pattern(regexp="[0-9]+$", message="contAmt는 숫자만 입력되어야 합니다.")
	private String contAmt;
	
	// 비고
	@Column(name="cont_note")
	private String contNote;
}
