package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.b2en.sms.dto.autocompleteinfo.B2enACInterface;
import com.b2en.sms.model.B2en;

@Repository
public interface B2enRepository extends JpaRepository<B2en, Integer>{
	
	List<B2enACInterface> findAllBy();
	
	@Query(value="SELECT * FROM b2en ORDER BY binary(emp_nm)", nativeQuery = true)
	List<B2en> findAllOrderByName();

}
