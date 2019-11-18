package com.b2en.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.b2en.springboot.repo.CustomerRepository;

@Controller
public class HomeController {
	
	CustomerRepository repository;
	
	@GetMapping(value="/")
	public String home(Model model) {
		model.addAttribute("message", "SMS Back-End Test");
		return "index";
	}
}
