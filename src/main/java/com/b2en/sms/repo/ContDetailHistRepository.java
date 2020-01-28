package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.model.ContDetailHist;
import com.b2en.sms.model.pk.ContDetailHistPK;

public interface ContDetailHistRepository extends JpaRepository<ContDetailHist, ContDetailHistPK>{

	ContDetailHist findByContDetailHistPKDetailSeq(int detailSeq);
	
	@Transactional
	void deleteByContDetailHistPKDetailSeq(int detailSeq);
	
	@Query(value="SELECT max(detail_seq) FROM cont_detail_hist", nativeQuery = true)
	Integer findMaxDetailSeq();
}
