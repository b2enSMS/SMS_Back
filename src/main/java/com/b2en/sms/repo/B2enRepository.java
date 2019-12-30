package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.dto.autocompleteinfo.B2enACInterface;
import com.b2en.sms.entity.B2en;

public interface B2enRepository extends JpaRepository<B2en, Integer>{
	
	List<B2enACInterface> findAllBy();
}
