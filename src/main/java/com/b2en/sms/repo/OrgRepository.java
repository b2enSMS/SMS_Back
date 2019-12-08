package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.Org;

public interface OrgRepository extends JpaRepository<Org, Long> {
	
	@Transactional
	void deleteByOrgId(int id);
	
	Org findByOrgId(int id);
	
	Org findByOrgNm(String name);
}
