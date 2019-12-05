package com.b2en.sms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prdt extends TimeEntity implements Serializable {

	// 제품
	private static final long serialVersionUID = -6488809586174402930L;

	// 제품ID
	@Id
	@Column(name="prdt_id")
	private String prdtId;
	
	// 제품명
	@Column(name="prdt_nm")
	private String prdtNm;
	
	// 제품버전
	@Column(name="prdt_ver")
	private String prdtVer;
	
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