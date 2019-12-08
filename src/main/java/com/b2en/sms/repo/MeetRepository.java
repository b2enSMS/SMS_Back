package com.b2en.sms.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.Meet;

public interface MeetRepository extends JpaRepository<Meet, Long>{

	@Transactional
	void deleteByMeetId(int id);
	
	Meet findByMeetId(int id);
}
