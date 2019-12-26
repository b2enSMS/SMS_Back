package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.TempVerHist;
import com.b2en.sms.entity.pk.TempVerHistPK;

public interface TempVerHistRepository extends JpaRepository<TempVerHist, TempVerHistPK> {
	
	public List<TempVerHist> findByTempVerHistPKTempVerId(int tempVerId);
	
}
