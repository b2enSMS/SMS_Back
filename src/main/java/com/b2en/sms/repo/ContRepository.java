package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.Cont;

public interface ContRepository extends JpaRepository<Cont, Integer>{
	
	List<Cont> findByDelYn(String yn);
	
	List<Cont> findByDelYnOrderByContIdDesc(String yn);
}
