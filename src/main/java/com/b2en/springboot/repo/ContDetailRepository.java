package com.b2en.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.b2en.springboot.entity.ContDetail;
import com.b2en.springboot.entity.pk.ContDetailPK;

public interface ContDetailRepository extends JpaRepository<ContDetail, ContDetailPK>{
	public ContDetail findByContDetailPKContSeq(int contSeq);
}
