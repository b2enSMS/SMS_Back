package com.b2en.sms.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6666083727643143267L;
	
	private String userName;
	
	public UserNotFoundException(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}

}