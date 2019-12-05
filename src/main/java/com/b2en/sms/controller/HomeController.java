package com.b2en.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.b2en.sms.repo.CustRepository;

@Controller
public class HomeController {
	
	CustRepository repository;
	
	@GetMapping(value="/")
	public String home(Model model) {
		model.addAttribute("message", "Ad infinitum");
		return "index";
	}
}