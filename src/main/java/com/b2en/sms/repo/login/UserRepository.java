package com.b2en.sms.repo.login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.model.login.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
