package com.b2en.springboot.entity.pk;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.b2en.springboot.entity.Cont;

import lombok.Data;

@Data
@Embeddable
public class ContChngHistPK implements Serializable {
	
	// 계약이력 PK
	private static final long serialVersionUID = -2866498436429053191L;

	// 이력순번
	@Column(name="hist_seq")
	private int histSeq;
	
	// 계약ID (FK)
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cont_id")
	private Cont cont;
}
