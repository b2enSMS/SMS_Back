package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.Cont;

public interface ContRepository extends JpaRepository<Cont, Long>{
	Cont findByContId(int id);
	
	@Transactional
	void deleteByContId(int id);
	
	List<Cont> findByDelYn(String yn);
	
	List<Cont> findByDelYnOrderByContIdDesc(String yn);
	
	@Query(value="SELECT max(cont_id) FROM cont", nativeQuery = true)
	int findMaxContId();
}
