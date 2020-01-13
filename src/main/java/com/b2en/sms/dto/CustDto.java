package com.b2en.sms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class CustDto {
	
	@Min(value = 1, message="orgId는 {value}보다 크거나 같아야 합니다.")
	private int orgId;
	
	@NotBlank(message="custNm이 빈칸입니다.")
	@Pattern(regexp="[가-힣|a-zA-Z]+$", message="custNm은 한글 또는 영문만 입력되어야 합니다.")
	private String custNm;
	
	@NotBlank(message="custRankNm이 빈칸입니다.")
	private String custRankNm;
	
	@NotBlank(message="email이 빈칸입니다.")
	@Email(message="올바른 이메일의 형식이 아닙니다.")
	private String email;
	
	@NotBlank(message="telNo가 빈칸입니다.")
	@Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}$", message="올바른 전화번호 형식이 아닙니다.")
	private String telNo;
	
	@NotBlank(message="custTpCd가 빈칸입니다.")
	private String custTpCd;
}
