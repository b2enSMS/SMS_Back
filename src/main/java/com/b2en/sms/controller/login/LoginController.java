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
import com.b2en.sms.dto.login.LoginResponse;
import com.b2en.sms.dto.toclient.ResponseInfo;
import com.b2en.sms.entity.login.Login;
import com.b2en.sms.repo.login.LoginRepository;

@RestController
@RequestMapping("/api/login")
public class LoginController {
	
	@Autowired
	private LoginRepository repositoryLogin;
	
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LoginResponse>> login(@Valid @RequestBody LoginInfo info, BindingResult result) {
		
		List<LoginResponse> lres = new ArrayList<LoginResponse>();
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		LoginResponse loginResponse = new LoginResponse();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 로그인에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			loginResponse.setInfo(res);
			lres.add(loginResponse);
			return new ResponseEntity<List<LoginResponse>>(lres, HttpStatus.UNAUTHORIZED);
		}

		Login storedInfo = repositoryLogin.findById(info.getUsername()).orElse(null);
		
		if(storedInfo==null) {
			res.add(new ResponseInfo("다음의 문제로 로그인에 실패했습니다: "));
			res.add(new ResponseInfo("등록되지 않은 이메일입니다."));
			loginResponse.setInfo(res);
			lres.add(loginResponse);
			return new ResponseEntity<List<LoginResponse>>(lres, HttpStatus.UNAUTHORIZED);
		}
		
		try {
			String inputHash = sha256(info.getPassword());
			String storedHash = storedInfo.getPassword();
			
			if(inputHash.equals(storedHash)) {
				String name = storedInfo.getName();
				String welcome = "환영합니다, " + name + " 님";
				res.add(new ResponseInfo(welcome));
				loginResponse.setInfo(res);
				loginResponse.setUsername(info.getUsername());
				loginResponse.set_id("ASDF");
				lres.add(loginResponse);
				return new ResponseEntity<List<LoginResponse>>(lres, HttpStatus.OK);
			} else {
				res.add(new ResponseInfo("다음의 문제로 로그인에 실패했습니다: "));
				res.add(new ResponseInfo("비밀번호가 틀렸습니다."));
				loginResponse.setInfo(res);
				lres.add(loginResponse);
				return new ResponseEntity<List<LoginResponse>>(lres, HttpStatus.UNAUTHORIZED);
			}
		} catch (NoSuchAlgorithmException e) {
			res.add(new ResponseInfo("다음의 문제로 로그인에 실패했습니다: "));
			res.add(new ResponseInfo("해싱에 사용되는 알고리즘에 문제가 있습니다. 관리자에게 문의하세요."));
			loginResponse.setInfo(res);
			lres.add(loginResponse);
			return new ResponseEntity<List<LoginResponse>>(lres, HttpStatus.SERVICE_UNAVAILABLE);
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
	
	/*
	 * @PostMapping(value = "/register", produces =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<List<ResponseInfo>>
	 * register(@Valid @RequestBody LoginInfo info, BindingResult result) {
	 * 
	 * List<ResponseInfo> res = new ArrayList<ResponseInfo>();
	 * 
	 * if (result.hasErrors()) { res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
	 * List<ObjectError> errors = result.getAllErrors(); for (int i = 0; i <
	 * errors.size(); i++) { res.add(new
	 * ResponseInfo(errors.get(i).getDefaultMessage())); } return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * if(repositoryLogin.existsById(info.getEmail())) { res.add(new
	 * ResponseInfo("다음의 문제로 등록에 실패했습니다: ")); res.add(new
	 * ResponseInfo("이미 등록된 이메일입니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * try { info.setPassword(sha256(info.getPassword())); } catch
	 * (NoSuchAlgorithmException e) { res.add(new
	 * ResponseInfo("다음의 문제로 등록에 실패했습니다: ")); res.add(new
	 * ResponseInfo("해싱에 사용되는 알고리즘에 문제가 있습니다. 관리자에게 문의하세요.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.SERVICE_UNAVAILABLE); }
	 * 
	 * Login entity = new Login(); entity.setEmail(info.getEmail());
	 * if(info.getUsername()==null || info.getUsername().equals("")) {
	 * entity.setUsername("이름없음"); } else { entity.setUsername(info.getUsername());
	 * } entity.setPassword(info.getPassword()); repositoryLogin.save(entity);
	 * 
	 * res.add(new ResponseInfo("등록에 성공했습니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK); }
	 * 
	 * @PutMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE) public
	 * ResponseEntity<List<ResponseInfo>> changeInfo(@Valid @RequestBody LoginInfo
	 * info, BindingResult result) {
	 * 
	 * List<ResponseInfo> res = new ArrayList<ResponseInfo>();
	 * 
	 * if (result.hasErrors()) { res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
	 * List<ObjectError> errors = result.getAllErrors(); for (int i = 0; i <
	 * errors.size(); i++) { res.add(new
	 * ResponseInfo(errors.get(i).getDefaultMessage())); } return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * Login toUpdate = repositoryLogin.findById(info.getEmail()).orElse(null);
	 * 
	 * if (toUpdate == null) { res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
	 * res.add(new ResponseInfo("등록되어있지 않은 이메일입니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * String hashedPassword; try { hashedPassword = sha256(info.getPassword()); }
	 * catch (NoSuchAlgorithmException e1) { res.add(new
	 * ResponseInfo("다음의 문제로 수정에 실패했습니다: ")); res.add(new
	 * ResponseInfo("해싱에 사용되는 알고리즘에 문제가 있습니다. 관리자에게 문의하세요.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.SERVICE_UNAVAILABLE); }
	 * 
	 * if(hashedPassword.equals(toUpdate.getPassword())) { res.add(new
	 * ResponseInfo("다음의 문제로 수정에 실패했습니다: ")); res.add(new
	 * ResponseInfo("새로 입력한 비밀번호가 이전 비밀번호와 동일합니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * if(info.getUsername()==null || info.getUsername().equals("")) {
	 * toUpdate.setUsername("이름없음"); } else {
	 * toUpdate.setUsername(info.getUsername()); }
	 * toUpdate.setPassword(hashedPassword); repositoryLogin.save(toUpdate);
	 * 
	 * res.add(new ResponseInfo("수정에 성공했습니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK); }
	 * 
	 * @DeleteMapping(value = "") public ResponseEntity<List<ResponseInfo>>
	 * delete(@RequestBody LoginInfo info) { List<ResponseInfo> res = new
	 * ArrayList<ResponseInfo>();
	 * 
	 * Login toDelete = repositoryLogin.findById(info.getEmail()).orElse(null);
	 * 
	 * if (toDelete == null) { res.add(new ResponseInfo("다음의 문제로 삭제에 실패했습니다: "));
	 * res.add(new ResponseInfo("등록되어있지 않은 이메일입니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * String hashedPassword; try { hashedPassword = sha256(info.getPassword()); }
	 * catch (NoSuchAlgorithmException e1) { res.add(new
	 * ResponseInfo("다음의 문제로 삭제에 실패했습니다: ")); res.add(new
	 * ResponseInfo("해싱에 사용되는 알고리즘에 문제가 있습니다. 관리자에게 문의하세요.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.SERVICE_UNAVAILABLE); }
	 * 
	 * if(!hashedPassword.equals(toDelete.getPassword())) { res.add(new
	 * ResponseInfo("다음의 문제로 삭제에 실패했습니다: ")); res.add(new
	 * ResponseInfo("비밀번호가 일치하지 않습니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST); }
	 * 
	 * repositoryLogin.deleteById(info.getEmail());
	 * 
	 * res.add(new ResponseInfo("삭제에 성공했습니다.")); return new
	 * ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK); }
	 */
	
}
