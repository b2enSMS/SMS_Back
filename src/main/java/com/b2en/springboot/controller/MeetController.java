package com.b2en.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.springboot.dto.MeetDto;
import com.b2en.springboot.dto.MeetDtoToClient;
import com.b2en.springboot.dto.OrgDtoToClient;
import com.b2en.springboot.dto.ResponseInfo;
import com.b2en.springboot.entity.Meet;
import com.b2en.springboot.entity.Org;
import com.b2en.springboot.repo.MeetRepository;
import com.b2en.springboot.repo.OrgRepository;

@RestController
@RequestMapping("/api/meet")
public class MeetController {
	
	@Autowired
	private MeetRepository repositoryM;
	
	@Autowired
	private OrgRepository repositoryO;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MeetDtoToClient>> showAll() {

		List<Meet> entityList = repositoryM.findAll();
		List<MeetDtoToClient> list;
		OrgDtoToClient org;

		list = modelMapper.map(entityList, new TypeToken<List<MeetDtoToClient>>() {
		}.getType());

		for(int i = 0; i < entityList.size(); i++) {
			org = modelMapper.map(entityList.get(i).getOrg(), OrgDtoToClient.class);
			list.get(i).setOrg(org);
		}
		
		return new ResponseEntity<List<MeetDtoToClient>>(list, HttpStatus.OK);

	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody MeetDto meet, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		 
		
		Meet meetEntity = modelMapper.map(meet, Meet.class);
		
		String orgId = meet.getOrgId(); // 해당 id를 갖는 Org 찾아서 저장
		Org org = repositoryO.findByOrgId(orgId);
		
		meetEntity.setOrg(org);
		
		repositoryM.save(meetEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {

		repositoryM.deleteByMeetId(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") String id, @Valid @RequestBody MeetDto meet, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Meet toUpdate = repositoryM.findByMeetId(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		String meetCnt = meet.getMeetCnt();

		toUpdate.setMeetCnt(meetCnt);

		repositoryM.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
