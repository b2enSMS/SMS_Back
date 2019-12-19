package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.ContDetail;
import com.b2en.sms.entity.pk.ContDetailPK;

public interface ContDetailRepository extends JpaRepository<ContDetail, ContDetailPK>{
	
	public ContDetail findByContDetailPKContSeq(int contSeq);
	
	public List<ContDetail> findByContDetailPKContId(int contId);
	
	@Transactional
	void deleteByContDetailPKContSeq(int contSeq);
	
	@Query(value="SELECT max(cont_seq) FROM cont_detail", nativeQuery = true)
	Integer findMaxContSeq();
}
