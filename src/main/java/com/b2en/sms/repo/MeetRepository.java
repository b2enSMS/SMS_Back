package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b2en.sms.model.Meet;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Integer>{

	List<Meet> findAllByOrderByMeetIdDesc();
	
}
