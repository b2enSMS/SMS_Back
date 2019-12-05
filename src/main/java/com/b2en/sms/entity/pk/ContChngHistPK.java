package com.b2en.sms.entity.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ContChngHistPK implements Serializable {
	
	// 계약이력 PK
	private static final long serialVersionUID = -2866498436429053191L;

	// 이력순번
	@Column(name="hist_seq")
	private int histSeq;
	
	// 계약ID (FK)
	private String contId;
}
