package com.b2en.sms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b2en.sms.model.CmmnCd;

@Repository
public interface CmmnCdRepository extends JpaRepository<CmmnCd, String>{
	
}
