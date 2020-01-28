package com.b2en.sms.dto.login;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

	@Data
	@NoArgsConstructor
    public static class Create {
		private String name;
		private String email;
		private String password;
		
		@Builder
		public Create(String name, String email, String password) {
			this.name = name;
			this.email = email;
			this.password = password;
		}
	}
	
	@Data
	@NoArgsConstructor
    public static class Response {
		private Long id;
		private String name;
		private String email;
		
		@Builder
		public Response(String name, String email) {
			this.name = name;
			this.email = email;
		}
	}
}