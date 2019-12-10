package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.dto.ContDto;
import com.b2en.sms.entity.Cont;

public class ContMap extends PropertyMap<ContDto, Cont>{
	
	// 매핑오류를 막기 위해 명시적 매핑
	@Override
	protected void configure() {
		
		// Cont.contId는 매핑하지 않음
		skip().setContId(0);
		
		// Cont.org는 매핑하지 않음
		skip().setOrg(null);
		
		// Cont.b2en은 매핑하지 않음
		skip().setB2en(null);
		
		// Cont.delYn은 매핑하지 않음
		skip().setDelYn(null);
	}
}
