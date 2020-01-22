package com.b2en.sms.entity.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Login {

	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
}
