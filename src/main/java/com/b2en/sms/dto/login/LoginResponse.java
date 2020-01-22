package com.b2en.sms.dto.login;

import lombok.Data;

@Data
public class LoginResponse {
	
	private AuthResponse auth;
	
	private String info;

}
