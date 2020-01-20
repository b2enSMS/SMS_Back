package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.entity.MeetAttendCust;
import com.b2en.sms.entity.pk.MeetAttendCustPK;

public interface MeetAttendCustRepository extends JpaRepository<MeetAttendCust, MeetAttendCustPK>{
	
	public MeetAttendCust findByMeetAttendCustPKCustSeq(int custSeq);
	
	List<MeetAttendCust> findByMeetAttendCustPKMeetId(int meetId);
	
	@Query(value="SELECT cust_id FROM meet_attend_cust WHERE meet_id = :meetId", nativeQuery = true)
	List<Integer> findCustIdByMeetId(@Param("meetId") int meetId);
	
	@Transactional
	void deleteByMeetAttendCustPKMeetId(int meetId);
	
	@Query(value="SELECT max(cust_seq) FROM meet_attend_cust", nativeQuery = true)
	Integer findMaxCustSeq();
	
	@Query(value = "SELECT DISTINCT org.org_nm FROM cust, org, meet_attend_cust WHERE cust.org_id = org.org_id AND cust.cust_id = meet_attend_cust.cust_id AND meet_attend_cust.meet_id = :meetId", nativeQuery = true)
	List<String> findOrg(@Param("meetId") int meetId);
	
	@Query(value="SELECT count(*) FROM meet_attend_cust WHERE cust_id = :custId", nativeQuery = true)
	Integer countByCustId(@Param("custId") int custId);
}
