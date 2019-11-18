package com.b2en.springboot.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Meeting extends TimeEntity implements Serializable {

	private static final long serialVersionUID = -2876114338764754032L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="meeting_id")
	private long meetingId;
	
	@Column(name="CUST_ID")
	private long custId;
	
	@Column(name="MEETING_MGR")
	private long meetingMgr;
	
	@Column(name="MEETING_DTM")
	private Date meetingDtm;
	
	@Column(name="MEETING_CONT")
	private String meetingCont;
}
