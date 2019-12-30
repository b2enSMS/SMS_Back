package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.dto.autocompleteinfo.OrgACInterface;
import com.b2en.sms.entity.Org;

public interface OrgRepository extends JpaRepository<Org, Integer> {
	
	List<OrgACInterface> findAllBy();
	
}
