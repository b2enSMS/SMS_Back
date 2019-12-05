package com.b2en.sms.entity.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ContDetailHistPK implements Serializable {

	// 계약상세이력 PK
	private static final long serialVersionUID = 1019006356562898173L;
	
	// 계약상세순번
	@Column(name = "detail_seq")
	private int detailSeq;
	
	// 계약상세 PK
	private ContDetailPK contDetailPK;

}
