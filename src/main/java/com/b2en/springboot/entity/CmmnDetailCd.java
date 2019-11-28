package com.b2en.springboot.entity;

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
public class CmmnDetailCd extends TimeEntity implements Serializable {

	// 공통상세코드
	private static final long serialVersionUID = 7671432056074942794L;

	// 공통상세코드
	@Id
	@Column(name="cmmn_detail_cd")
	private String cmmnDetailCd;
	
	// 공통코드 (FK)
	@Id
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cmmn_cd")
	private CmmnCd cmmnCd;
	
	// 공통상세코드명
	@Column(name="cmmn_detail_cd_nm")
	private String cmmnDetailCdNm;
	
	// 공통상세코드설명
	@Column(name="cmmn_detail_cd_desc")
	private String cmmnDetailCdDesc;
}
