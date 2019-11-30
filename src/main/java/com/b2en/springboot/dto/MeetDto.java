package com.b2en.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class MeetDto {

	private String meetId;
	
	@NotBlank(message="고객사 정보가 빈칸입니다.")
	private String orgId;
	
	@Pattern(regexp="^\\d{4}-\\d{2}-\\d{2}$", message="날짜는 yyyy-mm-dd의 형식으로 값이 입력되어야 합니다")
	private String meetDt;
	
	private String meetCnt;
	
	@Pattern(regexp="^\\d{2}:\\d{2}:\\d{2}$", message="시간은 hh:mm:ss의 형식으로 값이 입력되어야 합니다")
	private String meetStartTime;
	
	private String meetTotTime;
	
	//private String meetTpCd;
	
}
