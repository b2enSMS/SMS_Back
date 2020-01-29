package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.b2en.sms.model.MeetAttendEmp;
import com.b2en.sms.model.pk.MeetAttendEmpPK;

@Repository
public interface MeetAttendEmpRepository extends JpaRepository<MeetAttendEmp, MeetAttendEmpPK>{
	
	public MeetAttendEmp findByMeetAttendEmpPKEmpSeq(int empSeq);
	
	List<MeetAttendEmp> findByMeetAttendEmpPKMeetId(int meetId);
	
	@Query(value="SELECT emp_id FROM meet_attend_emp WHERE meet_id = :meetId", nativeQuery = true)
	List<Integer> findEmpIdByMeetId(@Param("meetId") int meetId);
	
	@Transactional
	void deleteByMeetAttendEmpPKMeetId(int meetId);
	
	@Query(value="SELECT max(emp_seq) FROM meet_attend_emp", nativeQuery = true)
	Integer findMaxEmpSeq();
	
	@Query(value="SELECT count(*) FROM meet_attend_emp WHERE emp_id = :empId", nativeQuery = true)
	Integer countByEmpId(@Param("empId") int empId);
}
