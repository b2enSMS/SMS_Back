package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.dto.ContDto;
import com.b2en.sms.entity.Cont;

public class ContMap extends PropertyMap<ContDto, Cont>{
	
	@Override
	protected void configure() {
		
		// Cont.contId는 매핑하지 않음
		skip().setContId(0);
		
		// Cont.org는 매핑하지 않음
		skip().setOrg(null);
		
		// Cont.b2en은 매핑하지 않음
		skip().setB2en(null);
		
		// Cont.delYn을 "N"으로 매핑
		map().setDelYn("N");
		
		map().setContReportNo("None");
		
		map().setContTpCd("None");
		
		map().setLcnsNo("None");
	}
}
