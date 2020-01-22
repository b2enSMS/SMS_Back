package com.b2en.sms.dto.login;

import java.util.List;

import com.b2en.sms.dto.toclient.ResponseInfo;

import lombok.Data;

@Data
public class LoginResponse {
	
	private List<AuthResponse> auth;
	
	private List<ResponseInfo> info;

}
