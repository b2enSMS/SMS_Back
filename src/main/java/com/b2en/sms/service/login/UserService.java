package com.b2en.sms.service.login;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.b2en.sms.dto.login.UserDto;
import com.b2en.sms.exception.UserDuplicateException;
import com.b2en.sms.model.login.Role;
import com.b2en.sms.model.login.User;
import com.b2en.sms.repo.login.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent()){
			throw new UsernameNotFoundException(email);
		}
		User userEntity = user.get();
		return new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_USER.getValue())));
	}

	public UserDto.Response addUser(UserDto.Create dto){
		log.debug("dto:{}", dto);
		Optional<User> checkUser = userRepository.findByEmail(dto.getEmail());
		if(checkUser.isPresent()) {
			throw new UserDuplicateException(dto.getEmail());
		}
		User newUser = modelMapper.map(dto, User.class);
		newUser.setPassword(passwordEncoder.encode((newUser.getPassword())));
		User user = userRepository.save(newUser);
		UserDto.Response result = modelMapper.map(user, UserDto.Response.class);
		log.debug("result:{}", result);
		return result;
	}
	
	public List<UserDto.Response> getUsers(){
		List<User> userList = userRepository.findAll();
		List<UserDto.Response> users = modelMapper.map(userList, new TypeToken<List<UserDto.Response>>(){}.getType());
		return users; 
	}
}