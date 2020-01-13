package com.b2en.sms.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.BatchSize;

import com.b2en.sms.entity.pk.ContDetailPK;

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
public class ContDetail extends TimeEntity implements Serializable {

	// 계약상세
	private static final long serialVersionUID = 5470656943389334288L;

	// 계약상세 PK
	@EmbeddedId
	private ContDetailPK contDetailPK;
	
	@MapsId("contId")
	@ManyToOne
	@JoinColumn(name="cont_id")
	//@OnDelete(action=OnDeleteAction.CASCADE)
	private Cont cont;
	
	// 라이센스ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="lcns_id")
	private Lcns lcns;
	
	// 계약금액
	@Column(name="cont_amt")
	private String contAmt;
	
	// 삭제여부
	@Column(name="del_yn")
	private String delYn;
	
	// 비고
	@Column(name="cont_note")
	private String contNote;
}
