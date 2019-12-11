package com.b2en.sms.dto;

import lombok.Data;

@Data
public class ContDetailDto {

	// 라이센스ID (FK)
	private int lcnsId;

	// 계약금액
	private String contAmt;
}
