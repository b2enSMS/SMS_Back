package com.b2en.springboot.entity.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ContDetailPK implements Serializable {

	// 계약상세 복합키
	private static final long serialVersionUID = 2947695454855718656L;

	// 계약순번
	@Column(name="cont_seq")
	private int contSeq;

	// 계약ID
	private String contId;
	
}
