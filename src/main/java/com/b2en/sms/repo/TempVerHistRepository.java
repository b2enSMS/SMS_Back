package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.b2en.sms.model.TempVerHist;
import com.b2en.sms.model.pk.TempVerHistPK;

@Repository
public interface TempVerHistRepository extends JpaRepository<TempVerHist, TempVerHistPK> {
	
	public List<TempVerHist> findByTempVerHistPKTempVerId(int tempVerId);
	
	@Transactional
	void deleteByTempVerHistPKTempVerId(int tempVerId);
	
	@Query(value="SELECT max(temp_ver_hist_seq) FROM temp_ver_hist", nativeQuery = true)
	Integer findMaxTempVerHistSeq();
	
}
