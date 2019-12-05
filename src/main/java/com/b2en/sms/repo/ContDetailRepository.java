package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.ContDetail;
import com.b2en.sms.entity.pk.ContDetailPK;

public interface ContDetailRepository extends JpaRepository<ContDetail, ContDetailPK>{
	
	public ContDetail findByContDetailPKContSeq(int contSeq);
	
	@Transactional
	void deleteByContDetailPKContSeq(int contSeq);
}
