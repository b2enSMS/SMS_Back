package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.entity.Lcns;
import com.b2en.sms.entity.LcnsChngHist;

public class LcnsChngHistMap extends PropertyMap<Lcns, LcnsChngHist> {

	@Override
	protected void configure() {
		// LcnsChngHist.lcnsChngHistPK는 매핑하지 않음
		skip().setLcnsChngHistPK(null);

		// LcnsChngHist.Lcns는 매핑하지 않음
		skip().setLcns(null);
	}

}
