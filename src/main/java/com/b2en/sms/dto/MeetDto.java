package com.b2en.sms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.b2en.sms.dto.toclient.MeetAttendCustDto;
import com.b2en.sms.dto.toclient.MeetAttendEmpDto;

import lombok.Data;

@Data
public class MeetDto {

	@NotBlank(message="미팅일시가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2]|[1-9])-(0[1-9]|[12]\\d|3[01]))$", message="미팅일시는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String meetDt;
	
	private String meetCnt;

	@NotBlank(message="미팅시작시간이 빈칸입니다.")
	@Pattern(regexp="^\\d{2}:\\d{2}$", message="미팅시작시간은 hh:mm의 형식으로 값이 입력되어야 합니다")
	private String meetStartTime;
	
	@NotBlank(message="미팅전체시간이 빈칸입니다.")
	private String meetTotTime;
	
	@NotBlank(message="미팅유형코드가 빈칸입니다.")
	private String meetTpCd;
	
	private MeetAttendCustDto[] custs;
	
	private MeetAttendEmpDto[] emps;
}
