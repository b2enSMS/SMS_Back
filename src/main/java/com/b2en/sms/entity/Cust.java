package com.b2en.sms.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.BatchSize;

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
public class Cust extends TimeEntity implements Serializable {
	
	// 고객사 담당자
	private static final long serialVersionUID = -1020705806990305070L;
	
	// 고객ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cust_id")
	private int custId;
	
	// 고객사ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
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
	
	// 고객구분코드(담당자인가, 사용자인가)
	@Column(name="cust_tp_cd")
	private String custTpCd;
	
}
