package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.Prdt;

public interface PrdtRepository extends JpaRepository<Prdt, Long>{
	
	@Transactional
	void deleteByPrdtId(int id);
	
	Prdt findByPrdtId(int id);
}
