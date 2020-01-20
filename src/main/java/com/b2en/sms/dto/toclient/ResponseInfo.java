package com.b2en.sms.dto.toclient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseInfo {
	private String info;
	
	public ResponseInfo(String info) {
		this.info = info;
	}
}
