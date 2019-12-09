package com.b2en.sms.entity.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Embeddable
public class MeetAttendCustPK implements Serializable {

	// 미팅참석자-고객사 PK
	private static final long serialVersionUID = -2148904292971251097L;

	// 순번
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_seq")
	private int custSeq;

	// 미팅ID
	private int meetId;
}
