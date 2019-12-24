package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.dto.LcnsDto;
import com.b2en.sms.entity.Lcns;

public class LcnsMap extends PropertyMap<LcnsDto, Lcns>{
	// 매핑오류를 막기 위해 명시적 매핑
	@Override
	protected void configure() {
		// Lcns.lcnsId는 매핑하지 않음
		skip().setLcnsId(0);
		
		// Lcns.prdt는 매핑하지 않음
		skip().setPrdt(null);
		
		// Lcns.scan은 매핑하지 않음
		skip().setScan(null);
	}

}
