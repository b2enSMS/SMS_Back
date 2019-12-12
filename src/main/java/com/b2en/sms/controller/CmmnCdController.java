package com.b2en.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.entity.CmmnCd;
import com.b2en.sms.repo.CmmnCdRepository;

@RestController
@RequestMapping("/api/cmmncd")
public class CmmnCdController {

	@Autowired
	private CmmnCdRepository repository;
	
	@GetMapping(value = "/cont/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CmmnCd>> showAll(@PathVariable("key") int key) {

		List<CmmnCd> list = repository.findAll();

		return new ResponseEntity<List<CmmnCd>>(list, HttpStatus.OK);

	}
}
