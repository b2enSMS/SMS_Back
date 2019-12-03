package com.b2en.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.springboot.repo.CmmnCdRepository;

@RestController
@RequestMapping("/api/cmmncd")
public class CmmnCdController {

	@Autowired
	private CmmnCdRepository repository;
}
