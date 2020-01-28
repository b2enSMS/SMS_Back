package com.b2en.sms.model;

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
public class CmmnCd extends TimeEntity implements Serializable {

	// 공통코드
	private static final long serialVersionUID = 3645271561367217852L;

	// 공통코드
	@Id
	@Column(name="cmmn_cd")
	private String cmmnCd;
	
	// 공통코드명
	@Column(name="cmmn_cd_nm")
	private String cmmnCdNm;
	
	// 공통코드설명
	@Column(name="cmmn_cd_desc")
	private String cmmnCdDesc;
}
