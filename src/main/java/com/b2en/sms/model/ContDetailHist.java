package com.b2en.sms.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.BatchSize;

import com.b2en.sms.model.pk.ContDetailHistPK;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@BatchSize(size=10)
public class ContDetailHist extends TimeEntity implements Serializable {

	// 계약상세이력
	private static final long serialVersionUID = -4264672109787511568L;

	// 계약상세이력 PK
	@EmbeddedId
	private ContDetailHistPK contDetailHistPK;
	
	@MapsId("contDetailPK")
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "cont_id", referencedColumnName="cont_id"),
		@JoinColumn(name = "cont_seq", referencedColumnName="cont_seq")
	})
	//@OnDelete(action=OnDeleteAction.CASCADE)
	private ContDetail contDetail;
	
	// 계약금액
	@Column(name = "cont_amt")
	private String contAmt;

	// 라이센스ID (FK)
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "lcns_id")
	private Lcns lcns;
	
	// 제품ID (FK)
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "prdt_id")
	private Prdt prdt;
	
	// 비고
	@Column(name = "cont_note")
	private String contNote;
	
}
