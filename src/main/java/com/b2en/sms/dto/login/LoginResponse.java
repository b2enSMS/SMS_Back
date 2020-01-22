package com.b2en.sms.dto.login;

import java.util.List;

import com.b2en.sms.dto.toclient.ResponseInfo;

import lombok.Data;

@Data
public class LoginResponse {
	
	// 이메일
	private String username;
	
	// ???
	private String _id;
	
	private List<ResponseInfo> info;

}
