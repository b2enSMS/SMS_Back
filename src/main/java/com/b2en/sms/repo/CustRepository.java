package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.dto.autocompleteinfo.CustACInterface;
import com.b2en.sms.entity.Cust;

public interface CustRepository extends JpaRepository<Cust, Integer>{
	
	List<CustACInterface> findAllBy();
	
	@Query(value="SELECT * FROM cust ORDER BY binary(cust_nm)", nativeQuery = true)
	List<Cust> findAllOrderByName();
	
	@Query(value="SELECT count(*) FROM cust WHERE org_id = :orgId", nativeQuery = true)
	Integer countByOrgId(@Param("orgId") int orgId);
	
	@Query(value="SELECT DISTINCT cust.* FROM cust, temp_ver WHERE cust.cust_id = temp_ver.cust_id AND cust.cust_id NOT IN (SELECT cust.cust_id FROM cust, cont WHERE cust.cust_id = cont.cust_id)", nativeQuery = true)
	List<Cust> findPreorderCust();
}
