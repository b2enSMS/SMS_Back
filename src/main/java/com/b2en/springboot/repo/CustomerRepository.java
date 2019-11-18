package com.b2en.springboot.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.b2en.springboot.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	Customer findByCompNm(String compNm);
	
	@Transactional
	void deleteByCompNm(String compNm);
}
