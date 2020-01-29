package com.b2en.sms.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class B2enDto {

	@Data
	@NoArgsConstructor
	public static class Request {
		
		@NotBlank(message="담당자명이 빈칸입니다.")
		@Pattern(regexp="[가-힣|a-zA-Z]+$", message="담당자명은 한글 또는 영문만 입력되어야 합니다.")
		private String empNm;
		
		private String empNo;
		
		@Email(message="올바른 이메일의 형식이 아닙니다.")
		private String email;
		
		@Pattern(regexp="^(\\d{2,3}-\\d{3,4}-\\d{4})|$", message="올바른 전화번호 형식이 아닙니다.")
		private String telNo;
		
		@NotBlank(message="역할구분코드가 빈칸입니다.")
		private String empTpCd;
		
		@Builder
		public Request(String empNm, String empNo, String email, String telNo, String empTpCd) {
			this.empNm = empNm;
			this.empNo = empNo;
			this.email = email;
			this.telNo = telNo;
			this.empTpCd = empTpCd;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class Response {
		
		private int empId;
		
		private String empNm;
		
		private String empNo;
		
		private String email;
		
		private String telNo;
		
		private String empTpCd;
		
		private String empTpCdNm;
		
		@Builder
		public Response(int empId, String empNm, String empNo, String email, String telNo, String empTpCd) {
			this.empId = empId;
			this.empNm = empNm;
			this.empNo = empNo;
			this.email = email;
			this.telNo = telNo;
			this.empTpCd = empTpCd;
		}
		
	}
}
