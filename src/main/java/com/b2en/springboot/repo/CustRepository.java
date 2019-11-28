package com.b2en.springboot.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.b2en.springboot.entity.Cust;

public interface CustRepository extends JpaRepository<Cust, Long>{
		
	Cust findByCustId(String id);
	
	@Transactional
	void deleteByCustId(String id);
}
