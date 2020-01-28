package com.b2en.sms.controller.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.login.UserDto;
import com.b2en.sms.exception.ExceptionResult;
import com.b2en.sms.exception.UserDuplicateException;
import com.b2en.sms.service.login.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
    private UserService userService;

	@PostMapping
	public ResponseEntity<UserDto.Response> addUser(@RequestBody UserDto.Create dto){
		log.debug("Create:{}", dto);
		
		UserDto.Response user = userService.addUser(dto);
		log.debug("user:{}", user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
    @GetMapping
    public List<UserDto.Response> getUsers() {
        return userService.getUsers();
    }
    
    @ExceptionHandler(UserDuplicateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResult handleUserDuplicateException(UserDuplicateException e) {
    	ExceptionResult exceptionResult = new ExceptionResult();
    	exceptionResult.setMessage("[" + e.getUserName() + "]이 사용 중인 계정입니다.");
    	exceptionResult.setErrCode("user.duplicate");
    	log.debug("{}", exceptionResult);
        return exceptionResult;
    }
}