package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.entity.ContDetail;
import com.b2en.sms.entity.pk.ContDetailPK;

public interface ContDetailRepository extends JpaRepository<ContDetail, ContDetailPK>{
	
	public ContDetail findByContDetailPKContSeq(int contSeq);
	
	public List<ContDetail> findByContDetailPKContId(int contId);
	
	@Query(value="SELECT * FROM cont_detail WHERE cont_id = :contId AND del_yn = 'N'", nativeQuery = true)
	public List<ContDetail> findByContIdWhereDelYnIsN(@Param("contId") int contId);
	
	@Transactional
	void deleteByContDetailPKContSeq(int contSeq);
	
	@Query(value="SELECT max(cont_seq) FROM cont_detail", nativeQuery = true)
	Integer findMaxContSeq();
}
