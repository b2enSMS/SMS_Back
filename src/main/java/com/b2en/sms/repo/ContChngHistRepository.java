package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.entity.ContChngHist;
import com.b2en.sms.entity.pk.ContChngHistPK;

public interface ContChngHistRepository extends JpaRepository<ContChngHist, ContChngHistPK>{
	
	public ContChngHist findByContChngHistPKHistSeq(int histSeq);
	
	public List<ContChngHist> findByContChngHistPKContId(int contId);
	
	@Transactional
	void deleteByContChngHistPKHistSeq(int histSeq);
	
	@Query(value="SELECT count(*) FROM cont_chng_hist WHERE cust_id = :custId", nativeQuery = true)
	Integer countByCustId(@Param("custId") int custId);
}
