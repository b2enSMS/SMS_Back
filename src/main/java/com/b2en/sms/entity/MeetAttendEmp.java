package com.b2en.sms.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.b2en.sms.entity.pk.MeetAttendEmpPK;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MeetAttendEmp extends TimeEntity implements Serializable {

	// 미팅참석자-비투엔
	private static final long serialVersionUID = -3669818810257096162L;

	// 미팅참석자-비투엔 PK
	@EmbeddedId
	private MeetAttendEmpPK meetAttendEmpPK;
	
	@MapsId("meetId")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="meet_id")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Meet meet;

	// 담당자ID
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "emp_id")
	private B2en b2en;
}
