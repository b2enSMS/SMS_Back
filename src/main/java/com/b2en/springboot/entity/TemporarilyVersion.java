package com.b2en.springboot.entity;

import java.io.Serializable;

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
public class TemporarilyVersion extends TimeEntity implements Serializable {

	private static final long serialVersionUID = -4863631099505337113L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="temp_lcns_id")
	private long tempLcnsId;
	
	@Column(name="PRDT_NM")
	private String prdtNm;
	
	@Column(name="TEMP_LCNS_VER")
	private String tempLcnsVer;

}
