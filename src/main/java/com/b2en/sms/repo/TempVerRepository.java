package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.b2en.sms.model.TempVer;

@Repository
public interface TempVerRepository extends JpaRepository<TempVer, Integer> {

	List<TempVer> findAllByOrderByTempVerIdDesc();
	
	@Query(value="SELECT count(*) FROM temp_ver WHERE cust_id = :custId", nativeQuery = true)
	Integer countByCustId(@Param("custId") int custId);
	
	@Query(value="SELECT count(*) FROM temp_ver WHERE emp_id = :empId", nativeQuery = true)
	Integer countByEmpId(@Param("empId") int empId);
}
