package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.model.CmmnDetailCd;
import com.b2en.sms.model.pk.CmmnDetailCdPK;

public interface CmmnDetailCdRepository extends JpaRepository<CmmnDetailCd, CmmnDetailCdPK>{
	List<CmmnDetailCd> findByCmmnDetailCdPKCmmnCd(String cmmnCd);
	CmmnDetailCd findByCmmnDetailCdPKCmmnDetailCd(String cmmnDetailCd);
}
