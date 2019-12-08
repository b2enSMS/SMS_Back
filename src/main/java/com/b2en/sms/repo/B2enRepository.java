package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.B2en;

public interface B2enRepository extends JpaRepository<B2en, Long>{
	
	B2en findByEmpId(int id);
	
	B2en findByEmpNm(String empNm);
	
	@Transactional
	void deleteByEmpId(int id);
}
