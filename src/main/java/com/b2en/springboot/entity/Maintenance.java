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
public class Maintenance extends TimeEntity implements Serializable {
	
	private static final long serialVersionUID = -1637767491313640577L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mntc_id")
	private long mntcId;
	
	@Column(name="MGR_ID")
	private long mgrId;
	
	@Column(name="CONT_ID")
	private long contId;
	
	@Column(name="MNTC_INFO")
	private String mntcInfo;
	
	@Column(name="MNTC_DTM")
	private Date mntcDtm;
	
	@Column(name="MNTC_TP")
	private String mntcTp;
}
