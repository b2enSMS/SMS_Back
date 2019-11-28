package com.b2en.springboot.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.b2en.springboot.entity.pk.ContDetailHistPK;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ContDetailHist extends TimeEntity implements Serializable {

	// 계약상세이력
	private static final long serialVersionUID = -4264672109787511568L;

	// 계약상세이력 PK
	@EmbeddedId
	private ContDetailHistPK contDetailHistPK;
	
	// 계약금액
	@Column(name = "cont_amt")
	private String contAmt;

	// 제품ID (FK)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "prdt_id")
	private Prdt prdt;
	
}
