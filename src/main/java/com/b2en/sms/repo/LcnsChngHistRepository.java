package com.b2en.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.model.LcnsChngHist;
import com.b2en.sms.model.pk.LcnsChngHistPK;

public interface LcnsChngHistRepository extends JpaRepository<LcnsChngHist, LcnsChngHistPK> {
	
	public List<LcnsChngHist> findByLcnsChngHistPKLcnsId(int lcnsId);
	
	@Query(value="SELECT max(hist_seq) FROM lcns_chng_hist", nativeQuery = true)
	Integer findMaxHistSeq();
}
