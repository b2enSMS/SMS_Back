package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.ContChngHist;
import com.b2en.sms.entity.pk.ContChngHistPK;

public interface ContChngHistRepository extends JpaRepository<ContChngHist, ContChngHistPK>{
	
	public ContChngHist findByContChngHistPKHistSeq(int histSeq);
	
	public List<ContChngHist> findByContChngHistPKContId(int contId);
	
	@Transactional
	void deleteByContChngHistPKHistSeq(int histSeq);
	
}
