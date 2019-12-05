package com.b2en.sms.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cust extends TimeEntity implements Serializable {
	
	// 고객사 담당자
	private static final long serialVersionUID = -1020705806990305070L;
	
	// 고객ID
	@Id
	@Column(name="cust_id")
	private String custId;
	
	// 고객사ID (FK)
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="org_id")
	private Org org;
	
	// 고객명
	@Column(name="cust_nm")
	private String custNm;
	
	// 직책
	@Column(name="cust_rank_nm")
	private String custRankNm;
	
	// 이메일
	@Column(name="email")
	private String email;
	
	// 전화번호
	@Column(name="tel_no")
	private String telNo;
	
	// 고객구분코드
	@Column(name="cust_tp_cd")
	private String custTpCd;
	
}
