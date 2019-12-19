package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.CmmnDetailCd;
import com.b2en.sms.entity.pk.CmmnDetailCdPK;

public interface CmmnDetailCdRepository extends JpaRepository<CmmnDetailCd, CmmnDetailCdPK>{
	List<CmmnDetailCd> findByCmmnDetailCdPKCmmnCd(String cmmnCd);
	CmmnDetailCd findByCmmnDetailCdPKCmmnDetailCd(String cmmnDetailCd);
}
