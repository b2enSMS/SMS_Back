package com.b2en.sms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.CmmnCd;

public interface CmmnCdRepository extends JpaRepository<CmmnCd, Long>{
	CmmnCd findByCmmnCd(String cmmnCd);
}
