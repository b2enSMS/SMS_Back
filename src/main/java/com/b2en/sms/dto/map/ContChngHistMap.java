package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.entity.Cont;
import com.b2en.sms.entity.ContChngHist;

public class ContChngHistMap extends PropertyMap<Cont, ContChngHist> {

	@Override
	protected void configure() {
		// ContChngHist.contChngHistPK는 매핑하지 않음
		skip().setContChngHistPK(null);

		// ContChngHist.Cont는 매핑하지 않음
		skip().setCont(null);
	}

}
