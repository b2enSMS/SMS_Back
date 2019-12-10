package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.Cont;

public interface ContRepository extends JpaRepository<Cont, Long>{
	Cont findByContId(int id);
	
	@Transactional
	void deleteByContId(int id);
	
	List<Cont> findByDelYn(String yn);
}
