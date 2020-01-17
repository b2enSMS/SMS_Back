package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.dto.autocompleteinfo.PrdtACInterface;
import com.b2en.sms.entity.Prdt;

public interface PrdtRepository extends JpaRepository<Prdt, Integer>{
	
	List<PrdtACInterface> findAllBy();
	
	@Query(value="SELECT prdt_id FROM prdt WHERE prdt_nm = :prdtNm", nativeQuery = true)
	int findPrdtIdByPrdtNm(@Param("prdtNm") String prdtNm);
}
