package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.TempVer;

public interface TempVerRepository extends JpaRepository<TempVer, Integer> {

	List<TempVer> findAllByOrderByTempVerIdDesc();
	
}
