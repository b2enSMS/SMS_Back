package com.b2en.sms.bean;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.b2en.sms.dto.map.ContChngHistMap;
import com.b2en.sms.dto.map.ContMap;
import com.b2en.sms.dto.map.CustMap;
import com.b2en.sms.dto.map.LcnsMap;

@Configuration
public class CommonBean {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper  = new ModelMapper();
		modelMapper.addMappings(new ContMap());
		modelMapper.addMappings(new ContChngHistMap());
		modelMapper.addMappings(new CustMap());
		modelMapper.addMappings(new LcnsMap());
		return modelMapper;
	}
	
}
