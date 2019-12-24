package com.b2en.sms.service.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScanResponse {
	
	private String name;
	
	private String status;
	
	private String url;
	
	private String thumbUrl;

}
