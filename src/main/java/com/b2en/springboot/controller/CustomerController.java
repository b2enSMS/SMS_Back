package com.b2en.springboot.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.springboot.dto.CustomerDto;
import com.b2en.springboot.entity.Customer;
import com.b2en.springboot.repo.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/allList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustomerDto>> getAll() {

		List<Customer> entityList = repository.findAll();
		List<CustomerDto> list;

		list = modelMapper.map(entityList, new TypeToken<List<CustomerDto>>() {
		}.getType());

		return new ResponseEntity<List<CustomerDto>>(list, HttpStatus.OK);

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody CustomerDto customer) {
		
		Customer customerEntity = modelMapper.map(customer, Customer.class);

		repository.save(customerEntity);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/findByCompany", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerDto> findByCompany(String compNm) {

		Customer customerEntity = repository.findByCompNm(compNm);

		CustomerDto customer;

		customer = modelMapper.map(customerEntity, CustomerDto.class);
		return new ResponseEntity<CustomerDto>(customer, HttpStatus.OK);
	}

	@GetMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(String compNm) {

		repository.deleteByCompNm(compNm);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@Valid CustomerDto customer, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		String company = customer.getCompNm();
		String managerName = customer.getMgrNm();
		Customer customerToUpdate = repository.findByCompNm(company);

		if (customerToUpdate == null) {
			customerToUpdate = new Customer();
		}

		customerToUpdate.setCompNm(company);
		customerToUpdate.setMgrNm(managerName);

		repository.save(customerToUpdate);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
