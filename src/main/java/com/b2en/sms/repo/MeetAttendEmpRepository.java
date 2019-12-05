package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.MeetAttendEmp;
import com.b2en.sms.entity.pk.MeetAttendEmpPK;

public interface MeetAttendEmpRepository extends JpaRepository<MeetAttendEmp, MeetAttendEmpPK>{
	public MeetAttendEmp findByMeetAttendEmpPKEmpSeq(int empSeq);
	
	@Transactional
	void deleteByMeetAttendEmpPKEmpSeq(int empSeq);
}
