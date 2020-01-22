package com.b2en.sms.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
	
	// 이메일
	private String username;
		
	// ???
	private String _id;
}
