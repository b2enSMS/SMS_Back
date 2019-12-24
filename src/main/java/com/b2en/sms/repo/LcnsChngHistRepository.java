package com.b2en.sms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b2en.sms.entity.LcnsChngHist;
import com.b2en.sms.entity.pk.LcnsChngHistPK;

public interface LcnsChngHistRepository extends JpaRepository<LcnsChngHist, LcnsChngHistPK> {
	@Query(value="SELECT max(hist_seq) FROM lcns_chng_hist", nativeQuery = true)
	Integer findMaxHistSeq();
}
