package com.b2en.sms.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Embeddable
public class ContChngHistPK implements Serializable {
	
	// 계약이력 PK
	private static final long serialVersionUID = -2866498436429053191L;

	// 이력순번
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="hist_seq")
	private int histSeq;
	
	// 계약ID (FK)
	private int contId;
}
