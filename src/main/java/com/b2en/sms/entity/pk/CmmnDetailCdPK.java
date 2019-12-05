package com.b2en.sms.entity.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class CmmnDetailCdPK implements Serializable {

	// 공통상세코드 PK
	private static final long serialVersionUID = 7775002160381337585L;

	// 공통상세코드
	@Column(name = "cmmn_detail_cd")
	private String cmmnDetailCd;

	// 공통코드 (FK)
	private String cmmnCd;
}
