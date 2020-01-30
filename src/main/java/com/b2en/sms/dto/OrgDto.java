package com.b2en.sms.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrgDto {
	
	@Data
	@NoArgsConstructor
	public static class Request {
		
		@NotBlank(message="고객사명이 빈칸입니다.")
		private String orgNm;
		
		private String orgAddr;
		
		@Builder
		public Request(String orgNm, String orgAddr) {
			this.orgNm = orgNm;
			this.orgAddr = orgAddr;
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class Response {
		
		private int orgId;
		
		private String orgNm;
		
		private String orgAddr;
		
		@Builder
		public Response(int orgId, String orgNm, String orgAddr) {
			this.orgId = orgId;
			this.orgNm = orgNm;
			this.orgAddr = orgAddr;
		}
	}
}
