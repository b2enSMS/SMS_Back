package com.b2en.springboot.entity.pk;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.b2en.springboot.entity.Meet;

import lombok.Data;

@Data
@Embeddable
public class MeetAttendCustPK implements Serializable {

	// 미팅참석자-고객사 PK
	private static final long serialVersionUID = -2148904292971251097L;

	// 순번
	@Column(name = "cust_seq")
	private int custSeq;

	// 미팅ID (FK)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "meet_id")
	private Meet meet;
}
