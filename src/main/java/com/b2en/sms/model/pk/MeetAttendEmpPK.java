package com.b2en.sms.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Embeddable
public class MeetAttendEmpPK implements Serializable {

	// 미팅참석자-비투엔 PK
	private static final long serialVersionUID = -3811932563260573661L;

	// 순번
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_seq")
	private int empSeq;

	// 미팅ID (FK)
	private int meetId;
}
