package com.b2en.sms.repo.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b2en.sms.entity.file.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
	
}
