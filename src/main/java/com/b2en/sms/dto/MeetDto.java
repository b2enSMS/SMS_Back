package com.b2en.sms.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MeetDto {

	@Data
	@NoArgsConstructor
	public static class Request {
		
		@NotBlank(message="미팅일시가 빈칸입니다.")
		@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="미팅일시는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
		private String meetDt;
		
		private String meetCnt;

		@NotBlank(message="미팅시작시간이 빈칸입니다.")
		private String meetStartTime;
		
		@NotBlank(message="미팅전체시간이 빈칸입니다.")
		@Min(value = 0, message="{value} 이상의 값이 입력되어야 합니다.")
		private String meetTotTime;
		
		@NotBlank(message="미팅유형코드가 빈칸입니다.")
		private String meetTpCd;
		
		private MeetAttendCustDto[] custs;
		
		private MeetAttendEmpDto[] emps;
		
		@Builder
		public Request(String meetDt, String meetCnt, String meetStartTime, String meetTotTime, String meetTpCd) {
			this.meetDt = meetDt;
			this.meetCnt = meetCnt;
			this.meetStartTime = meetStartTime;
			this.meetTotTime = meetTotTime;
			this.meetTpCd = meetTpCd;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class ResponseList {
		
		private int meetId;

		private String meetDt;
		
		private String meetCnt;

		private String meetStartTime;
		
		private String meetTotTime;
		
		private String meetTpCd;
		
		private String meetTpCdNm;
		
		private String OrgNm;
		
		private String custNm;
		
		private String empNm;
		
		@Builder
		public ResponseList(String meetDt, String meetCnt, String meetStartTime, String meetTotTime, String meetTpCd) {
			this.meetDt = meetDt;
			this.meetCnt = meetCnt;
			this.meetStartTime = meetStartTime;
			this.meetTotTime = meetTotTime;
			this.meetTpCd = meetTpCd;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class ResponseOne {
		
		private int meetId;

		private String meetDt;
		
		private String meetCnt;

		private String meetStartTime;
		
		private String meetTotTime;
		
		private String meetTpCd;
		
		private String meetTpCdNm;
		
		private MeetAttendCustDto[] custs;
		
		private MeetAttendEmpDto[] emps;
		
		@Builder
		public ResponseOne(String meetDt, String meetCnt, String meetStartTime, String meetTotTime, String meetTpCd) {
			this.meetDt = meetDt;
			this.meetCnt = meetCnt;
			this.meetStartTime = meetStartTime;
			this.meetTotTime = meetTotTime;
			this.meetTpCd = meetTpCd;
		}
		
	}
	
}
