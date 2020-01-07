package com.b2en.sms.dto;

import lombok.Data;

@Data
public class MeetDto {

	//@NotBlank(message="meetDt가 빈칸입니다.")
	//@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String meetDt;
	
	//@NotBlank(message="meetCnt가 빈칸입니다.")
	private String meetCnt;

	//@NotBlank(message="meetStartTime이 빈칸입니다.")
	//@Pattern(regexp="^\\d{2}:\\d{2}:\\d{2}$", message="시간은 hh:mm:ss의 형식으로 값이 입력되어야 합니다")
	private String meetStartTime;
	
	//@NotBlank(message="meetTotTime이 빈칸입니다.")
	private String meetTotTime;
	
	//@NotBlank(message="meetTpCd가 빈칸입니다.")
	private String meetTpCd;
	
	private int[] custId;
	
	private int[] empId;
}
