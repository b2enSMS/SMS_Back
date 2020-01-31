package com.b2en.sms.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.b2en.sms.customvalidator.StartEndValid;
import com.b2en.sms.dto.file.FileList;
import com.b2en.sms.dto.file.FileListToClient;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LcnsDtoNew {

	@Data
	@NoArgsConstructor
	@StartEndValid.List(value = {
			@StartEndValid(start="lcnsStartDt", end="lcnsEndDt", message="라이센스 시작일과 라이센스 종료일의 선후관계가 맞지 않습니다.") }
	)
	public static class Request {
		
		private int contSeq;

		@Min(value = 1, message="제품이 선택되지 않았습니다.")
		private int prdtId;

		@NotBlank(message="라이센스번호가 빈칸입니다.")
		private String lcnsNo;

		@NotBlank(message="라이센스 발행일이 빈칸입니다.")
		@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스 발행일은 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
		private String lcnsIssuDt;
		
		@NotBlank(message="라이센스유형코드가 빈칸입니다.")
		private String lcnsTpCd;
		
		private String lcnsTpNm;

		@NotBlank(message="증명번호가 빈칸입니다.")
		private String certNo;
		
		@NotBlank(message="라이센스개시일자가 빈칸입니다.")
		@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스개시일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
		private String lcnsStartDt;
		
		@NotBlank(message="라이센스종료일자가 빈칸입니다.")
		@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스종료일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
		private String lcnsEndDt;
		
		private FileList[] fileList;
		
		@Min(value = 0, message="{value} 이상의 값이 입력되어야 합니다.")
		private String contAmt;
		
		private String contNote;
		
		@Builder
		public Request(int prdtId, String lcnsNo, String certNo, String lcnsIssuDt, String lcnsTpCd, String lcnsStartDt, String lcnsEndDt) {
			this.prdtId = prdtId;
			this.lcnsNo = lcnsNo;
			this.certNo = certNo;
			this.lcnsIssuDt = lcnsIssuDt;
			this.lcnsTpCd = lcnsTpCd;
			this.lcnsStartDt = lcnsStartDt;
			this.lcnsEndDt = lcnsEndDt;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	@StartEndValid.List(value = {
			@StartEndValid(start="lcnsStartDt", end="lcnsEndDt", message="라이센스 시작일과 라이센스 종료일의 선후관계가 맞지 않습니다.") }
	)
	public static class RequestTemp {
		
		private int lcnsId;
		
		@Min(value = 1, message="제품이 선택되지 않았습니다.")
		private int prdtId;
		
		@NotBlank(message="라이센스 발행일이 빈칸입니다.")
		@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스 발행일은 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
		private String lcnsIssuDt;
		
		@NotBlank(message="라이센스유형코드가 빈칸입니다.")
		private String lcnsTpCd;
		
		@NotBlank(message="라이센스개시일자가 빈칸입니다.")
		@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스개시일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
		private String lcnsStartDt;
		
		@NotBlank(message="라이센스종료일자가 빈칸입니다.")
		@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]|[1-9]))$", message="라이센스종료일자는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
		private String lcnsEndDt;
		
		@Builder
		public RequestTemp(int prdtId, String lcnsIssuDt, String lcnsTpCd, String lcnsStartDt, String lcnsEndDt) {
			this.prdtId = prdtId;
			this.lcnsIssuDt = lcnsIssuDt;
			this.lcnsTpCd = lcnsTpCd;
			this.lcnsStartDt = lcnsStartDt;
			this.lcnsEndDt = lcnsEndDt;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class Response {
		
		private int lcnsId;
	
		private int contSeq;
	
		private int prdtId;
		
		private String prdtNm;
	
		private String lcnsNo;
	
		private String lcnsIssuDt;
		
		private String lcnsTpCd;
		
		private String lcnsTpNm;
	
		private String certNo;
		
		private String lcnsStartDt;
	
		private String lcnsEndDt;
		
		private FileListToClient[] fileList;
		
		private String contAmt;
		
		private String contNote;
		
		@Builder
		public Response(int prdtId, String lcnsNo, String certNo, String lcnsIssuDt, String lcnsTpCd, String lcnsStartDt, String lcnsEndDt) {
			this.prdtId = prdtId;
			this.lcnsNo = lcnsNo;
			this.certNo = certNo;
			this.lcnsIssuDt = lcnsIssuDt;
			this.lcnsTpCd = lcnsTpCd;
			this.lcnsStartDt = lcnsStartDt;
			this.lcnsEndDt = lcnsEndDt;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class ResponseTemp {
		
		private int lcnsId;

		private int prdtId;
		
		private String prdtNm;

		private String lcnsIssuDt;
		
		private String lcnsTpCd;
		
		private String lcnsTpNm;
		
		private String lcnsStartDt;

		private String lcnsEndDt;
		
		@Builder
		public ResponseTemp(int prdtId, String lcnsIssuDt, String lcnsTpCd, String lcnsStartDt, String lcnsEndDt) {
			this.prdtId = prdtId;
			this.lcnsIssuDt = lcnsIssuDt;
			this.lcnsTpCd = lcnsTpCd;
			this.lcnsStartDt = lcnsStartDt;
			this.lcnsEndDt = lcnsEndDt;
		}
		
	}
	
}
