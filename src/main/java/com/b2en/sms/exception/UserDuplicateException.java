package com.b2en.sms.exception;

public class UserDuplicateException extends RuntimeException {

	private static final long serialVersionUID = 689868506601839036L;

	private String userName;
	
	public UserDuplicateException(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}

}