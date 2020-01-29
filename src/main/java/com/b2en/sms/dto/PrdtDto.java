package com.b2en.sms.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PrdtDto {
	
	@Data
	@NoArgsConstructor
	public static class Request {
		
		@NotBlank(message="제품명이 빈칸입니다.")
		private String prdtNm;
		
		private String prdtDesc;
		
		@Min(value = 0, message="{value} 이상의 값이 입력되어야 합니다.")
		private String prdtAmt;
		
		@NotBlank(message="제품구분코드가 빈칸입니다.")
		private String prdtTpCd;
		
		@Builder
		public Request(String prdtNm, String prdtDesc, String prdtAmt, String prdtTpCd) {
			this.prdtNm = prdtNm;
			this.prdtDesc = prdtDesc;
			this.prdtAmt = prdtAmt;
			this.prdtTpCd = prdtTpCd;
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class Response {
		
		private int prdtId;

		private String prdtNm;
		
		private String prdtDesc;
		
		private String prdtAmt;
		
		private String prdtTpCd;
		
		private String prdtTpCdNm;
		
		@Builder
		public Response(String prdtNm, String prdtDesc, String prdtAmt, String prdtTpCd) {
			this.prdtNm = prdtNm;
			this.prdtDesc = prdtDesc;
			this.prdtAmt = prdtAmt;
			this.prdtTpCd = prdtTpCd;
		}
	}
}
