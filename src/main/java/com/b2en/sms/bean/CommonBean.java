package com.b2en.sms.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.b2en.sms.dto.map.ContChngHistMap;
import com.b2en.sms.dto.map.ContMap;

@Configuration
public class CommonBean {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper  = new ModelMapper();
		modelMapper.addMappings(new ContMap());
		modelMapper.addMappings(new ContChngHistMap());
		return modelMapper;
	}
	
}
