package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.dto.autocompleteinfo.ContACInterface;
import com.b2en.sms.entity.Cont;

public interface ContRepository extends JpaRepository<Cont, Integer>{
	
	Cont findByHeadContId(int headContId);
	
	List<Cont> findByDelYn(String yn);
	
	List<Cont> findByDelYnOrderByContIdDesc(String yn);
	
	List<ContACInterface> findAllBy();
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO cont(cust_id, org_id, emp_id, head_cont_id, cont_nm, cont_dt, cont_tot_amt, del_yn, cont_report_no, cont_tp_cd, install_dt, check_dt, mtnc_start_dt, mtnc_end_dt, created_date, modified_date) VALUES(null, :orgId, :empId, :#{#paramCont.headContId}, :#{#paramCont.contNm}, :#{#paramCont.contDt}, :#{#paramCont.contTotAmt}, 'N', :#{#paramCont.contReportNo}, :#{#paramCont.contTpCd}, :#{#paramCont.installDt}, :#{#paramCont.checkDt}, :#{#paramCont.mtncStartDt}, :#{#paramCont.mtncEndDt}, :#{#paramCont.createdDate}, :#{#paramCont.modifiedDate})", nativeQuery = true)
	int forceInsert(@Param("paramCont") Cont cont, @Param("orgId") int orgId, @Param("empId") int empId);

	@Query(value="SELECT * FROM cont WHERE cont_id = (SELECT max(cont_id) FROM cont)", nativeQuery = true)
	Cont findRecentCont();
}
