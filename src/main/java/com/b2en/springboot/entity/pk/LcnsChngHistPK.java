package com.b2en.springboot.entity.pk;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.b2en.springboot.entity.Lcns;

import lombok.Data;

@Data
@Embeddable
public class LcnsChngHistPK implements Serializable {

	// 라이센스이력 PK
	private static final long serialVersionUID = -3793193088834928125L;

	// 이력순번
	@Column(name="hist_seq")
	private int histSeq;
		
	// 라이센스ID (FK)
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="lcns_id")
	private Lcns lcns;
}
