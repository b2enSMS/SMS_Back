package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.TempVerHist;
import com.b2en.sms.entity.pk.TempVerHistPK;

public interface TempVerHistRepository extends JpaRepository<TempVerHist, TempVerHistPK> {
	
	public List<TempVerHist> findByTempVerHistPKTempVerId(int tempVerId);
	
	@Transactional
	void deleteByTempVerHistPKTempVerId(int tempVerId);
	
	@Query(value="SELECT max(temp_ver_hist_seq) FROM temp_ver_hist", nativeQuery = true)
	Integer findMaxTempVerHistSeq();
	
}
