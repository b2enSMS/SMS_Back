package com.b2en.springboot.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonBean {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper  = new ModelMapper();
		return modelMapper;
	}
	
}
