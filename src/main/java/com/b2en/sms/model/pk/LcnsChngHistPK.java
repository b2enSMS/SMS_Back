package com.b2en.sms.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Embeddable
public class LcnsChngHistPK implements Serializable {

	// 라이센스이력 PK
	private static final long serialVersionUID = -3793193088834928125L;

	// 이력순번
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="hist_seq")
	private int histSeq;
		
	// 라이센스ID
	private int lcnsId;
}
