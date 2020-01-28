package com.b2en.sms.controller.login;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.login.AuthDto;
import com.b2en.sms.dto.toclient.ResponseInfo;
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
	public ResponseEntity<AuthDto.Response> check(HttpServletRequest req) {
		log.debug("{}", "LOGIN CHECK!");
		AuthDto.Response res = authService.checkResponse(req);
    	return ResponseEntity.ok().body(res);
    }
	
	@PostMapping("/login")
    public ResponseEntity<AuthDto.Response> login(@RequestBody AuthDto.Request dto) {
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
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ResponseInfo>> handleUserNotFoundException(UserNotFoundException e) {
		/*
		 * ExceptionResult exceptionResult = new ExceptionResult();
		 * exceptionResult.setMessage("[" + e.getUserName() + "]에 해당하는 계정이 없습니다.");
		 * exceptionResult.setErrCode("user.not.found.exception"); log.debug("{}",
		 * exceptionResult); return exceptionResult;
		 */
    	List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		res.add(new ResponseInfo("다움의 이유로 로그인에 실패했습니다: "));
		res.add(new ResponseInfo("[" + e.getUserName() + "]에 해당하는 계정이 없습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(IncorrectPasswordException.class)
	//@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<List<ResponseInfo>> handleIncorrectPasswordException(IncorrectPasswordException e) {
		/*
		 * ExceptionResult exceptionResult = new ExceptionResult();
		 * exceptionResult.setMessage("잘못된 비밀번호입니다.");
		 * exceptionResult.setErrCode("user.incorrect.password.exception");
		 * log.debug("{}", exceptionResult); return exceptionResult;
		 */
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		res.add(new ResponseInfo("다움의 이유로 로그인에 실패했습니다: "));
		res.add(new ResponseInfo("잘못된 비밀번호입니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
	}
}