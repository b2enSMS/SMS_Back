package com.b2en.springboot.entity.pk;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.b2en.springboot.entity.Cont;
import com.b2en.springboot.entity.ContDetail;

import lombok.Data;

@Data
@Embeddable
public class ContDetailHistPK implements Serializable {

	// 계약상세이력 복합키
	private static final long serialVersionUID = 1019006356562898173L;

	// 계약순번 (FK)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cont_seq")
	private ContDetail contDetail;

	// 계약ID (FK)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cont_id")
	private Cont cont;
	
	// 계약상세순번
	@Column(name = "detail_seq")
	private int detailSeq;

}
