package com.b2en.sms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Prdt extends TimeEntity implements Serializable {

	// 제품
	private static final long serialVersionUID = -6488809586174402930L;

	// 제품ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="prdt_id")
	private int prdtId;
	
	// 제품명
	@Column(name="prdt_nm")
	private String prdtNm;
	
	// 제품버전
	//@Column(name="prdt_ver")
	//private String prdtVer;
	
	// 제품설명
	@Column(name="prdt_desc")
	private String prdtDesc;
	
	// 제품단가
	@Column(name="prdt_amt")
	private String prdtAmt;
	
	// 제품구분코드
	@Column(name="prdt_tp_cd")
	private String prdtTpCd;
}
