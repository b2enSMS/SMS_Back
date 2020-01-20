package com.b2en.sms.repo.login;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.login.Login;

public interface LoginRepository extends JpaRepository<Login, String> {
	
}
