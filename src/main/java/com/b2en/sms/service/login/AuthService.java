package com.b2en.sms.service.login;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.b2en.sms.dto.login.AuthDto;
import com.b2en.sms.exception.IncorrectPasswordException;
import com.b2en.sms.exception.UserNotFoundException;
import com.b2en.sms.model.login.Role;
import com.b2en.sms.model.login.User;
import com.b2en.sms.repo.login.UserRepository;
import com.b2en.sms.security.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider tokenProvider;

	public AuthDto.Response login(AuthDto.Request dto) {
		String email = dto.getEmail();
		log.debug("Email:{}", email);
		Optional<User> loginUser = userRepository.findByEmail(email);

		if (!loginUser.isPresent()) {
			throw new UserNotFoundException(email);
		}

		User user = loginUser.get();
		if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			System.out.println(dto.getPassword() + " " + user.getPassword());
			throw new IncorrectPasswordException();
		}
		AuthDto.Response authResult = modelMapper.map(user, AuthDto.Response.class);
		String token = tokenProvider.createToken(user.getEmail(), Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_USER.getValue())));
		authResult.setToken(token);
		return authResult;
	}
	
	public AuthDto.Response checkResponse(HttpServletRequest req) {
    	String token = tokenProvider.resolveToken(req).replace("Bearer ", "");
    	String email = tokenProvider.getEmail(token);
    	
    	User user = userRepository.findByEmail(email).get();
    	AuthDto.Response response = modelMapper.map(user, AuthDto.Response.class);
    	response.setToken(token);
    	return response;
    }
}