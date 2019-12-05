package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.ContDetailHist;
import com.b2en.sms.entity.pk.ContDetailHistPK;

public interface ContDetailHistRepository extends JpaRepository<ContDetailHist, ContDetailHistPK>{

	ContDetailHist findByContDetailHistPKDetailSeq(int detailSeq);
	
	@Transactional
	void deleteByContDetailHistPKDetailSeq(int detailSeq);
}
