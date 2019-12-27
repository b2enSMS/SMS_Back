package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.MeetAttendEmp;
import com.b2en.sms.entity.pk.MeetAttendEmpPK;

public interface MeetAttendEmpRepository extends JpaRepository<MeetAttendEmp, MeetAttendEmpPK>{
	
	public MeetAttendEmp findByMeetAttendEmpPKEmpSeq(int empSeq);
	
	List<MeetAttendEmp> findByMeetAttendEmpPKMeetId(int meetId);
	
	@Transactional
	void deleteByMeetAttendEmpPKEmpSeq(int empSeq);
}
