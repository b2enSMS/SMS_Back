package com.b2en.sms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.BatchSize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@BatchSize(size=10)
public class Org extends TimeEntity implements Serializable {

	// 고객사
	private static final long serialVersionUID = 298954421302276841L;
	
	// 고객사ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="org_id")
	private int orgId;
	
	// 고객사명
	@Column(name="org_nm")
	private String orgNm;
	
	// 주소
	@Column(name="org_addr")
	private String orgAddr;
}
