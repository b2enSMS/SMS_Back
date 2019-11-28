package com.b2en.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.springboot.entity.Cont;

public interface ContRepository extends JpaRepository<Cont, Long>{
	Cont findByContId(String id);
}
