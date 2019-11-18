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
public class TempVerManagement extends TimeEntity implements Serializable {

	private static final long serialVersionUID = -2412628975418317412L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="temp_mngmnt_id")
	private long tempMngmntId;
	
	@Column(name="CUST_ID")
	private long custId;
	
	@Column(name="LCNS_ISSU_DTM")
	private Date lcnsIssuDtm;
	
	@Column(name="LCNS_EXDT")
	private Date lcnsExdt;
	
	@Column(name="LCNS_KEY")
	private String lcnsKey;
	
	@Column(name="MGR_ID")
	private String mgrId;
	
	@Column(name="TEMP_LCNS_ID")
	private long tempLcnsId;
	
	@Column(name="ETC_INFO")
	private String etcInfo;
}
