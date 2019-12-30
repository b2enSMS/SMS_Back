package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.MeetAttendEmp;
import com.b2en.sms.entity.pk.MeetAttendEmpPK;

public interface MeetAttendEmpRepository extends JpaRepository<MeetAttendEmp, MeetAttendEmpPK>{
	
	public MeetAttendEmp findByMeetAttendEmpPKEmpSeq(int empSeq);
	
	List<MeetAttendEmp> findByMeetAttendEmpPKMeetId(int meetId);
	
	@Transactional
	void deleteByMeetAttendEmpPKEmpSeq(int empSeq);
	
	@Transactional
	void deleteByMeetAttendEmpPKMeetId(int meetId);
	
	@Query(value="SELECT max(emp_seq) FROM meet_attend_emp", nativeQuery = true)
	Integer findMaxEmpSeq();
}
