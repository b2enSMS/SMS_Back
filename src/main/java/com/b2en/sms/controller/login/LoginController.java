package com.b2en.sms.controller.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.login.LoginInfo;
import com.b2en.sms.dto.toclient.ResponseInfo;
import com.b2en.sms.entity.login.Login;
import com.b2en.sms.repo.login.LoginRepository;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Autowired
	private LoginRepository repositoryLogin;
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> login(@Valid @RequestBody LoginInfo info, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 로그인에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}

		Login storedInfo = repositoryLogin.findById(info.getEmail()).orElse(null);
		
		if(storedInfo==null) {
			res.add(new ResponseInfo("다음의 문제로 로그인에 실패했습니다: "));
			res.add(new ResponseInfo("등록되지 않은 이메일입니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		try {
			String inputHash = sha256(info.getPassword());
			String storedHash = storedInfo.getPassword();
			
			if(inputHash.equals(storedHash)) {
				String username = storedInfo.getUsername();
				String welcome = "환영합니다, " + username + " 님";
				res.add(new ResponseInfo(welcome));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
			} else {
				res.add(new ResponseInfo("다음의 문제로 로그인에 실패했습니다: "));
				res.add(new ResponseInfo("비밀번호가 틀렸습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
		} catch (NoSuchAlgorithmException e) {
			res.add(new ResponseInfo("다음의 문제로 로그인에 실패했습니다: "));
			res.add(new ResponseInfo("해싱에 사용되는 알고리즘에 문제가 있습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	private String sha256(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(input.getBytes());
		
		byte[] bytes = md.digest();
		
		StringBuilder sb = new StringBuilder();
	    for (byte b: bytes) {
	      sb.append(String.format("%02x", b));
	    }

		return sb.toString();
	}
	
	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> register(@Valid @RequestBody LoginInfo info, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		if(repositoryLogin.existsById(info.getEmail())) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			res.add(new ResponseInfo("해당 이메일은 이미 등록되었습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		try {
			info.setPassword(sha256(info.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			res.add(new ResponseInfo("해싱에 사용되는 알고리즘에 문제가 있습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		Login entity = new Login();
		entity.setEmail(info.getEmail());
		if(info.getUsername()==null || info.getUsername().equals("")) {
			entity.setUsername("이름없음");
		} else {
			entity.setUsername(info.getUsername());
		}
		entity.setPassword(info.getPassword());
		repositoryLogin.save(entity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
