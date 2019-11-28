package com.b2en.springboot.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.springboot.entity.B2en;

public interface B2enRepository extends JpaRepository<B2en, Long>{
	
	B2en findByEmpId(String id);
	
	@Transactional
	void deleteByEmpId(String id);
}
