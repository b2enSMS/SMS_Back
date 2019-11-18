package com.b2en.springboot.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.springboot.dto.LicenseClientDto;
import com.b2en.springboot.dto.LicenseServerDto;
import com.b2en.springboot.entity.License;
import com.b2en.springboot.entity.Solution;
import com.b2en.springboot.repo.LicenseRepository;
import com.b2en.springboot.repo.SolutionRepository;

@RestController
@RequestMapping("/license")
public class LicenseController {

	@Autowired
	private LicenseRepository repository;
	
	@Autowired
	private SolutionRepository solRepository;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/allList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LicenseServerDto>> getAll() {

		List<License> entityList = repository.findAll();
		List<LicenseServerDto> list;

		list = modelMapper.map(entityList, new TypeToken<List<LicenseServerDto>>() {
		}.getType());

		return new ResponseEntity<List<LicenseServerDto>>(list, HttpStatus.OK);

	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<Void> create(LicenseClientDto license) {

		LicenseServerDto licenseServer = new LicenseServerDto();
		String licenseKey = license.getLcnsKey();
		long solutionId = license.getSolution();
		Solution solution = solRepository.findByPrdtId(solutionId);
		
		licenseServer.setLcnsKey(licenseKey);
		licenseServer.setSolution(solution);
		
		License licenseEntity = modelMapper.map(licenseServer, License.class);
		repository.save(licenseEntity);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
