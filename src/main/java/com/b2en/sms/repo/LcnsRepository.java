package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.Lcns;

public interface LcnsRepository extends JpaRepository<Lcns, Long>{
	
	Lcns findByLcnsId(int id);
	
	@Transactional
	void deleteByLcnsId(int id);
	
	@Query(value="SELECT * FROM lcns ORDER BY lcns_id DESC LIMIT 1", nativeQuery = true)
	Lcns findNewest();
}
