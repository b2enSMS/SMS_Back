package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b2en.sms.entity.MeetAttendEmp;
import com.b2en.sms.entity.pk.MeetAttendEmpPK;

public interface MeetAttendEmpRepository extends JpaRepository<MeetAttendEmp, MeetAttendEmpPK>{
	
	public MeetAttendEmp findByMeetAttendEmpPKEmpSeq(int empSeq);
	
	List<MeetAttendEmp> findByMeetAttendEmpPKMeetId(int meetId);
	
	@Query(value="SELECT emp_id FROM meet_attend_emp WHERE meet_id = :meetId", nativeQuery = true)
	List<Integer> findEmpIdByMeetId(@Param("meetId") int meetId);
	
	@Transactional
	void deleteByMeetAttendEmpPKEmpSeq(int empSeq);
	
	@Transactional
	void deleteByMeetAttendEmpPKMeetId(int meetId);
	
	@Query(value="SELECT max(emp_seq) FROM meet_attend_emp", nativeQuery = true)
	Integer findMaxEmpSeq();
	
	
}
