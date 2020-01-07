package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.Meet;

public interface MeetRepository extends JpaRepository<Meet, Integer>{

	List<Meet> findAllByOrderByMeetIdDesc();
	
	@Query(value = "SELECT meet_id FROM meet", nativeQuery = true)
	List<Integer> findMeetId();
	
}
