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
public class MaintenanceManager extends TimeEntity implements Serializable {

	private static final long serialVersionUID = 5102590877176244724L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mgr_id")
	private long mgrId;
	
	@Column(name="MGR_NM")
	private String mgrNm;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="TEL_NO")
	private String telNo;
}
