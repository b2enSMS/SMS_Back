package com.b2en.springboot.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class MeetingDto {

	private long meetingId;

	private long custId;

	private long meetingMgr;

	private Date meetingDtm;

	private String meetingCont;
}
