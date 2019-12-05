package com.b2en.sms.entity.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class LcnsChngHistPK implements Serializable {

	// 라이센스이력 PK
	private static final long serialVersionUID = -3793193088834928125L;

	// 이력순번
	@Column(name="hist_seq")
	private int histSeq;
		
	// 라이센스ID
	private String lcnsId;
}
