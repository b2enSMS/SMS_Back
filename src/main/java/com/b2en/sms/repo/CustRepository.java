package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.Cust;

public interface CustRepository extends JpaRepository<Cust, Integer>{
	
	@Query(value="SELECT * FROM cust ORDER BY binary(cust_nm)", nativeQuery = true)
	List<Cust> findAllOrderByName();

}
