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
public class Contract extends TimeEntity implements Serializable {

	private static final long serialVersionUID = -5171381164879310748L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cont_id")
	private long contId;
	
	@Column(name="LCNS_ID")
	private long lcnsId;
	
	@Column(name="CUST_ID")
	private long custId;
	
	@Column(name="MNTC_ISSU_DTM")
	private Date mntcIssuDtm;
	
	@Column(name="MNTC_EXDT")
	private Date mntcExdt;
	
	@Column(name="CONT_DTM")
	private Date contDtm;
}
