package com.b2en.springboot.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.springboot.entity.Meet;

public interface MeetRepository extends JpaRepository<Meet, Long>{

	@Transactional
	void deleteByMeetId(String id);
	
	Meet findByMeetId(String id);
}
