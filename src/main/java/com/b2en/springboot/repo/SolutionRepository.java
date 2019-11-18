package com.b2en.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.springboot.entity.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long>{
	Solution findByPrdtId(long id); 
}
