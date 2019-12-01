package com.b2en.springboot.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.b2en.springboot.entity.pk.MeetAttendCustPK;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MeetAttendCust extends TimeEntity implements Serializable {

	// 미팅참석자-고객사
	private static final long serialVersionUID = 1617411154620233800L;

	// 미팅참석자-고객사 PK
	@EmbeddedId
	private MeetAttendCustPK meetAttendCustPK;
	
	@MapsId("meetId")
	@ManyToOne
	@JoinColumn(name="meet_id")
	private Meet meet;

	// 고객ID
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cust_id")
	private Cust cust;
}
