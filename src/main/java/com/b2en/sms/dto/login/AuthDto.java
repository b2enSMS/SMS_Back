package com.b2en.sms.dto.login;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDto {

	@Data
	@NoArgsConstructor
    public static class Request {
		private String email;
		private String password;
		
		@Builder
		public Request(String email, String password) {
			this.email = email;
			this.password = password;
		}
	}
	
	@Data
	@NoArgsConstructor
    public static class Response {
		private String name;
		private String email;
		private String token;
		
		@Builder
		public Response(String name, String email) {
			this.name = name;
			this.email = email;
		}
	}	
}