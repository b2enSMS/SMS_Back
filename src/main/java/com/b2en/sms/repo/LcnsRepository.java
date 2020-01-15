package com.b2en.sms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.entity.Lcns;

public interface LcnsRepository extends JpaRepository<Lcns, Integer>{
	
	@Query(value="SELECT * FROM lcns ORDER BY lcns_id DESC LIMIT 1", nativeQuery = true)
	Lcns findNewest();
	
	@Query(value="SELECT count(*) FROM lcns WHERE prdt_id = :prdtId", nativeQuery = true)
	Integer countByPrdtId(@Param("prdtId") int prdtId);
}
