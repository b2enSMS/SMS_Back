package com.b2en.springboot.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.springboot.entity.Org;

public interface OrgRepository extends JpaRepository<Org, Long> {
	
	@Transactional
	void deleteByOrgId(String id);
	
	Org findByOrgId(String id);
}
