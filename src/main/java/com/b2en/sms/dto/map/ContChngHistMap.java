package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.model.Cont;
import com.b2en.sms.model.ContChngHist;

public class ContChngHistMap extends PropertyMap<Cont, ContChngHist> {

	// 매핑오류를 막기 위해 명시적 매핑
	@Override
	protected void configure() {
		// ContChngHist.contChngHistPK는 매핑하지 않음
		skip().setContChngHistPK(null);

		// ContChngHist.Cont는 매핑하지 않음
		skip().setCont(null);
	}

}
