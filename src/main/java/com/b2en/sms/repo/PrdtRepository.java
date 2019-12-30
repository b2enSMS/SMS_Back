package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.dto.autocompleteinfo.PrdtACInterface;
import com.b2en.sms.entity.Prdt;

public interface PrdtRepository extends JpaRepository<Prdt, Integer>{
	
	List<PrdtACInterface> findAllBy();
	
}
