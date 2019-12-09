package com.b2en.sms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.sms.entity.LcnsChngHist;
import com.b2en.sms.entity.pk.LcnsChngHistPK;

public interface LcnsChngHistRepository extends JpaRepository<LcnsChngHist, LcnsChngHistPK> {

}
