package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.dto.MeetDto;
import com.b2en.sms.model.Meet;

public class MeetMap extends PropertyMap<MeetDto, Meet> {

	@Override
	protected void configure() {
		// Meet.meetId는 매핑하지 않음
		skip().setMeetId(0);
	}

}
