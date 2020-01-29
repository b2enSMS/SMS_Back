package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b2en.sms.model.ContChngHist;
import com.b2en.sms.model.pk.ContChngHistPK;

@Repository
public interface ContChngHistRepository extends JpaRepository<ContChngHist, ContChngHistPK>{
	
	public ContChngHist findByContChngHistPKHistSeq(int histSeq);
	
	public List<ContChngHist> findByContChngHistPKContId(int contId);
	
	@Transactional
	void deleteByContChngHistPKHistSeq(int histSeq);
	
}
