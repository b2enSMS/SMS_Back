package com.b2en.sms.dto.file;

import lombok.Data;

@Data
public class FileListToClient {
	
	private String uid;
	
	private String name;
	
	private String status;
	
	private String url;
	
	private String thumbUrl;
	
	private Headers headers;
}
