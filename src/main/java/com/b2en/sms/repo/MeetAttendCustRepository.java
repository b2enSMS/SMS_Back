package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.MeetAttendCust;
import com.b2en.sms.entity.pk.MeetAttendCustPK;

public interface MeetAttendCustRepository extends JpaRepository<MeetAttendCust, MeetAttendCustPK>{
	
	public MeetAttendCust findByMeetAttendCustPKCustSeq(int custSeq);
	
	List<MeetAttendCust> findByMeetAttendCustPKMeetId(int meetId);
	
	@Transactional
	void deleteByMeetAttendCustPKCustSeq(int custSeq);
	
	@Transactional
	void deleteByMeetAttendCustPKMeetId(int meetId);
	
	@Query(value="SELECT max(cust_seq) FROM meet_attend_cust", nativeQuery = true)
	Integer findMaxCustSeq();
}
