package com.b2en.springboot.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.b2en.springboot.entity.pk.CmmnDetailCdPK;

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

	// 공통상세코드 PK
	@EmbeddedId
	private CmmnDetailCdPK cmmnDetailCdPK;
	
	@MapsId("cmmnCd")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cmmd_cd")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private CmmnCd cmmnCd;
	
	// 공통상세코드명
	@Column(name="cmmn_detail_cd_nm")
	private String cmmnDetailCdNm;
	
	// 공통상세코드설명
	@Column(name="cmmn_detail_cd_desc")
	private String cmmnDetailCdDesc;
}
