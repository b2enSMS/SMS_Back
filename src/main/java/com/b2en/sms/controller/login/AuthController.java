package com.b2en.sms.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.login.AuthDto;
import com.b2en.sms.exception.ExceptionResult;
import com.b2en.sms.exception.IncorrectPasswordException;
import com.b2en.sms.exception.UserNotFoundException;
import com.b2en.sms.service.login.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@GetMapping("/check")
	public ResponseEntity<Boolean> check() {
    	log.debug("{}", "LOGIN CHECK!");
    	return ResponseEntity.ok().body(Boolean.TRUE);
    }
	
	@PostMapping("/login")
    public ResponseEntity<AuthDto.Response> login(AuthDto.Request dto) {
    	log.debug("login:{}", dto);
    	AuthDto.Response authResult = authService.login(dto);
    	return ResponseEntity.ok().body(authResult);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout() {
    	log.debug("{}", "LOGOUT SUCCESS!");
    	return ResponseEntity.ok().body(Boolean.TRUE);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResult handleUserNotFoundException(UserNotFoundException e) {
    	ExceptionResult exceptionResult = new ExceptionResult();
    	exceptionResult.setMessage("[" + e.getUserName() + "]에 해당하는 계정이 없습니다.");
    	exceptionResult.setErrCode("user.not.found.exception");
    	log.debug("{}", exceptionResult);
        return exceptionResult;
    }

	@ExceptionHandler(IncorrectPasswordException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResult handleIncorrectPasswordException(IncorrectPasswordException e) {
		ExceptionResult exceptionResult = new ExceptionResult();
		exceptionResult.setMessage("잘못된 비밀번호입니다.");
		exceptionResult.setErrCode("user.incorrect.password.exception");
		log.debug("{}", exceptionResult);
		return exceptionResult;
	}
}