package com.b2en.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.springboot.entity.License;

public interface LicenseRepository extends JpaRepository<License, Long> {
	
}
