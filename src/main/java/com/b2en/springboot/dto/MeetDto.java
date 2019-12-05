package com.b2en.springboot.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class MeetDto {

	private String meetId;

	private String orgId;

	private String meetDt;
	
	private String meetCnt;

	private String meetStartTime;
	
	private String meetTotTime;
	
	//private String meetTpCd;
	
}
