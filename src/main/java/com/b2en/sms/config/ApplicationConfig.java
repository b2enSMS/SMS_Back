package com.b2en.sms.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.b2en.sms.dto.map.ContChngHistMap;
import com.b2en.sms.dto.map.ContMap;
import com.b2en.sms.dto.map.CustMap;
import com.b2en.sms.dto.map.LcnsChngHistMap;
import com.b2en.sms.dto.map.LcnsMap;
import com.b2en.sms.dto.map.LcnsToClientMap;
import com.b2en.sms.dto.map.MeetMap;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper  = new ModelMapper();
		modelMapper.addMappings(new ContMap());
		modelMapper.addMappings(new ContChngHistMap());
		modelMapper.addMappings(new CustMap());
		modelMapper.addMappings(new LcnsMap());
		modelMapper.addMappings(new LcnsChngHistMap());
		modelMapper.addMappings(new LcnsToClientMap());
		modelMapper.addMappings(new MeetMap());
		return modelMapper;
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
