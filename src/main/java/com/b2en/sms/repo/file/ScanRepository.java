package com.b2en.sms.repo.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.b2en.sms.model.file.Scan;

@Repository
public interface ScanRepository extends JpaRepository<Scan, String> {

}
