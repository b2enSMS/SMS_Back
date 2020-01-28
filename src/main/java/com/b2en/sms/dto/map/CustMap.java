package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.dto.CustDto;
import com.b2en.sms.model.Cust;

public class CustMap extends PropertyMap<CustDto, Cust>{

	// 매핑오류를 막기 위해 명시적 매핑
	@Override
	protected void configure() {
		// Cust.custId는 매핑하지 않음
		skip().setCustId(0);
				
		// Cust.org는 매핑하지 않음
		skip().setOrg(null);
	}

}
