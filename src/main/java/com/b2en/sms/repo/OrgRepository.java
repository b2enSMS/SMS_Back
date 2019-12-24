package com.b2en.sms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.Org;

public interface OrgRepository extends JpaRepository<Org, Integer> {
	
}
