package com.b2en.sms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Org extends TimeEntity implements Serializable {

	// 고객사
	private static final long serialVersionUID = 298954421302276841L;
	
	// 고객사ID
	@Id
	@Column(name="org_id")
	private String orgId;
	
	// 고객사명
	@Column(name="org_nm")
	private String orgNm;
	
	// 주소
	@Column(name="org_addr")
	private String orgAddr;
}
