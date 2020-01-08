package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.dto.autocompleteinfo.OrgACInterface;
import com.b2en.sms.entity.Org;

public interface OrgRepository extends JpaRepository<Org, Integer> {
	
	List<OrgACInterface> findAllBy();
	
	@Query(value="SELECT * FROM org ORDER BY binary(org_nm)", nativeQuery = true)
	List<Org> findAllOrderByName();
	
	@Transactional
	@Modifying
	@Query(value="UPDATE org SET org_nm = :#{#paramOrg.orgNm}, org_addr = :#{#paramOrg.orgAddr} WHERE org_id = :#{#paramOrg.orgId}", nativeQuery = true)
	int forceUpdate(@Param("paramOrg") Org org);
}
