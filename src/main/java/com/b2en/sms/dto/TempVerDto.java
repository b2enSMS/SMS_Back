package com.b2en.sms.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.b2en.sms.dto.toclient.LcnsDtoToClientTempVer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TempVerDto {
	
	@Data
	@NoArgsConstructor
	public static class Request {
		
		@Min(value = 1, message="기관 담당자가 선택되지 않았습니다.")
		private int custId;

		@Min(value = 1, message="담당자가 선택되지 않았습니다.")
		private int empId;
		
		private String user;
		
		@NotBlank(message="MAC주소가 빈칸입니다.")
		private String macAddr;
		
		@NotBlank(message="요청일이 빈칸입니다.")
		@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="요청일은 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
		private String requestDate;
		
		private String issueReason;
		
		@Valid
		private LcnsDtoNew.RequestTemp[] lcns;
		
		@Builder
		public Request(String user, String macAddr, String requestDate, String issueReason) {
			this.user = user;
			this.macAddr = macAddr;
			this.requestDate = requestDate;
			this.issueReason = issueReason;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class ResponseList {
		
		private int tempVerId;
		
		private String orgNm;
		
		private String custNm;
		
		private String user;
		
		private String empNm;
		
		private String macAddr;
		
		private String requestDate;
		
		private String lcnsEndDate;
		
		private String issueReason;
		
		private boolean tight;

		public void setTight(boolean tight) {
			this.tight = tight;
		}
		
		@Builder
		public ResponseList(String user, String macAddr, String requestDate, String issueReason) {
			this.user = user;
			this.macAddr = macAddr;
			this.requestDate = requestDate;
			this.issueReason = issueReason;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class ResponseOne {
		
		private int tempVerId;
		
		private int custId;
		
		private String custNm;
		
		private String user;

		private int empId;
		
		private String empNm;
		
		private String macAddr;
		
		private String requestDate;
		
		private String issueReason;
		
		private LcnsDtoToClientTempVer[] lcns;
		
		@Builder
		public ResponseOne(String user, String macAddr, String requestDate, String issueReason) {
			this.user = user;
			this.macAddr = macAddr;
			this.requestDate = requestDate;
			this.issueReason = issueReason;
		}
		
	}
	
}
