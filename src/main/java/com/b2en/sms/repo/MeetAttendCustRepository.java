package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.MeetAttendCust;
import com.b2en.sms.entity.pk.MeetAttendCustPK;

public interface MeetAttendCustRepository extends JpaRepository<MeetAttendCust, MeetAttendCustPK>{
	
	public MeetAttendCust findByMeetAttendCustPKCustSeq(int custSeq);
	
	List<MeetAttendCust> findByMeetAttendCustPKMeetId(int meetId);
	
	@Transactional
	void deleteByMeetAttendCustPKCustSeq(int custSeq);
}
