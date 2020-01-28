package com.b2en.sms.dto.map;

import org.modelmapper.PropertyMap;

import com.b2en.sms.dto.toclient.LcnsDtoToClient;
import com.b2en.sms.model.Lcns;

public class LcnsToClientMap extends PropertyMap<Lcns, LcnsDtoToClient> {
	// 매핑오류를 막기 위해 명시적 매핑
	@Override
	protected void configure() {
		// LcnsDtoToClient.prdtId는 매핑하지 않음
		skip().setPrdtId(0);
		
		// LcnsDtoToClient.prdtNm은 매핑하지 않음
		skip().setPrdtNm(null);
		
		// LcnsDtoToClient.contAmt는 매핑하지 않음
		skip().setContAmt(null);
		
		// LcnsDtoToClient.lcnsTpNm은 매핑하지 않음
		skip().setLcnsTpNm(null);
		
		// LcnsDtoToClient.fileList는 매핑하지 않음
		skip().setFileList(null);
	}

}
