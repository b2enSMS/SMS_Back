package com.b2en.sms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.CustDto;
import com.b2en.sms.dto.DeleteDto;
import com.b2en.sms.dto.autocompleteinfo.CustAC;
import com.b2en.sms.dto.toclient.CustDtoToClient;
import com.b2en.sms.dto.toclient.ResponseInfo;
import com.b2en.sms.entity.CmmnDetailCd;
import com.b2en.sms.entity.Cont;
import com.b2en.sms.entity.Cust;
import com.b2en.sms.entity.Org;
import com.b2en.sms.repo.CmmnDetailCdRepository;
import com.b2en.sms.repo.ContRepository;
import com.b2en.sms.repo.CustRepository;
import com.b2en.sms.repo.MeetAttendCustRepository;
import com.b2en.sms.repo.OrgRepository;
import com.b2en.sms.repo.TempVerRepository;

@RestController
@RequestMapping("/api/cust")
public class CustController {

	@Autowired
	private CustRepository repositoryCust;
	@Autowired
	private ContRepository repositoryCont;
	@Autowired
	private TempVerRepository repositoryT;
	@Autowired
	private OrgRepository repositoryO;
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	@Autowired
	private MeetAttendCustRepository repositoryMAC;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustDtoToClient>> showAll() {

		List<Cust> entityList = repositoryCust.findAllOrderByName();
		if(entityList.size()==0) {
			return new ResponseEntity<List<CustDtoToClient>>(new ArrayList<CustDtoToClient>(), HttpStatus.OK);
		}
		List<CustDtoToClient> list;
		int orgId;
		String orgNm;
		HashMap<String, String> cmmnDetailCdMap = new HashMap<String, String>();
		List<CmmnDetailCd> cmmnDetailCdList = repositoryCDC.findByCmmnDetailCdPKCmmnCd("cust_tp_cd");
		for(int i = 0; i < cmmnDetailCdList.size(); i++) {
			cmmnDetailCdMap.put(cmmnDetailCdList.get(i).getCmmnDetailCdPK().getCmmnDetailCd(), cmmnDetailCdList.get(i).getCmmnDetailCdNm());
		}

		list = modelMapper.map(entityList, new TypeToken<List<CustDtoToClient>>() {
		}.getType());
		
		for(int i = 0; i < entityList.size(); i++) {
			orgId = entityList.get(i).getOrg().getOrgId();
			orgNm = entityList.get(i).getOrg().getOrgNm();
			list.get(i).setOrgId(orgId);
			list.get(i).setOrgNm(orgNm);
			list.get(i).setCustTpCdNm(cmmnDetailCdMap.get(entityList.get(i).getCustTpCd()));
		}

		return new ResponseEntity<List<CustDtoToClient>>(list, HttpStatus.OK);

	}
	
	// 계약고객(cont에 cust_id가 있는 cust)
	@GetMapping(value = "/cont", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustDtoToClient>> showCont() {

		List<Cont> contList = repositoryCont.findByDelYn("N");
		if(contList.size()==0) {
			return new ResponseEntity<List<CustDtoToClient>>(new ArrayList<CustDtoToClient>(), HttpStatus.OK);
		}
		List<Cust> entityList = new ArrayList<Cust>();
		List<CustDtoToClient> list;
		for(int i = 0; i < contList.size(); i++) {
			Cust contCust = contList.get(i).getCust();
			if(contCust == null || entityList.contains(contCust)) {
				continue;
			}
			entityList.add(contCust); // 계약고객만 리스트에 추가
		}

		list = modelMapper.map(entityList, new TypeToken<List<CustDtoToClient>>() {
		}.getType());
		
		HashMap<String, String> cmmnDetailCdMap = new HashMap<String, String>();
		List<CmmnDetailCd> cmmnDetailCdList = repositoryCDC.findByCmmnDetailCdPKCmmnCd("cust_tp_cd");
		for(int i = 0; i < cmmnDetailCdList.size(); i++) {
			cmmnDetailCdMap.put(cmmnDetailCdList.get(i).getCmmnDetailCdPK().getCmmnDetailCd(), cmmnDetailCdList.get(i).getCmmnDetailCdNm());
		}

		for(int i = 0; i < entityList.size(); i++) {
			list.get(i).setOrgId(entityList.get(i).getOrg().getOrgId());
			list.get(i).setOrgNm(entityList.get(i).getOrg().getOrgNm());
			list.get(i).setCustTpCdNm(cmmnDetailCdMap.get(entityList.get(i).getCustTpCd()));
		}
		
		return new ResponseEntity<List<CustDtoToClient>>(list, HttpStatus.OK);

	}
	
	// 가망고객(temp에 cust_id가 있고 cont에 없는 cust)
	@GetMapping(value = "/presale", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustDtoToClient>> showPresale() {

		List<Cust> presaleList = repositoryCust.findPreorderCust();
		if(presaleList.size()==0) {
			return new ResponseEntity<List<CustDtoToClient>>(new ArrayList<CustDtoToClient>(), HttpStatus.OK);
		}
		List<CustDtoToClient> list = modelMapper.map(presaleList, new TypeToken<List<CustDtoToClient>>() {
		}.getType());

		HashMap<String, String> cmmnDetailCdMap = new HashMap<String, String>();
		List<CmmnDetailCd> cmmnDetailCdList = repositoryCDC.findByCmmnDetailCdPKCmmnCd("cust_tp_cd");
		for (int i = 0; i < cmmnDetailCdList.size(); i++) {
			cmmnDetailCdMap.put(cmmnDetailCdList.get(i).getCmmnDetailCdPK().getCmmnDetailCd(),
					cmmnDetailCdList.get(i).getCmmnDetailCdNm());
		}

		for (int i = 0; i < presaleList.size(); i++) {
			list.get(i).setOrgId(presaleList.get(i).getOrg().getOrgId());
			list.get(i).setOrgNm(presaleList.get(i).getOrg().getOrgNm());
			list.get(i).setCustTpCdNm(cmmnDetailCdMap.get(presaleList.get(i).getCustTpCd()));
		}

		return new ResponseEntity<List<CustDtoToClient>>(list, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/aclist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CustAC>> acList() {

		List<Cust> list = repositoryCust.findAllOrderByName();
		if(list == null) {
			return new ResponseEntity<List<CustAC>>(new ArrayList<CustAC>(), HttpStatus.OK);
		}
		List<CustAC> acList = new ArrayList<CustAC>();
		
		for(int i = 0; i < list.size(); i++) {
			CustAC custAC = new CustAC();
			custAC.setCustId(list.get(i).getCustId());
			custAC.setCustNm(list.get(i).getCustNm()+" "+list.get(i).getOrg().getOrgNm());
			acList.add(custAC);
		}
		
		return new ResponseEntity<List<CustAC>>(acList, HttpStatus.OK);

	}
	 
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustDtoToClient> findById(@PathVariable("id") int id) {
		
		Cust cust = repositoryCust.findById(id).orElse(null);
		if(cust==null) {
			CustDtoToClient nothing = null;
			return new ResponseEntity<CustDtoToClient>(nothing, HttpStatus.OK);
		}
		
		CustDtoToClient custDtoToClient = modelMapper.map(cust, CustDtoToClient.class);
		custDtoToClient.setOrgId(cust.getOrg().getOrgId());
		custDtoToClient.setOrgNm(cust.getOrg().getOrgNm());
		String custTpCdNm = repositoryCDC.findByCmmnDetailCdPKCmmnDetailCd(cust.getCustTpCd()).getCmmnDetailCdNm();
		custDtoToClient.setCustTpCdNm(custTpCdNm);
		
		return new ResponseEntity<CustDtoToClient>(custDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody CustDto cust, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Cust custEntity = modelMapper.map(cust, Cust.class);
		
		int orgId = cust.getOrgId();
		Org org = repositoryO.getOne(orgId);
		
		custEntity.setOrg(org);
		
		repositoryCust.save(custEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "")
	public ResponseEntity<List<ResponseInfo>> delete(@RequestBody DeleteDto id) {
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();

		int[] idx = id.getIdx();
		for(int i = 0; i < idx.length; i++) {
			if(!repositoryCust.existsById(idx[i])) {
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(idx[i]+"의 id를 가지는 row가 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			if(repositoryCont.countByCustId(idx[i])>0) {
				String custNm = repositoryCust.findById(idx[i]).orElse(null).getCustNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(custNm+"을(를) 참조하는 계약이 있어 삭제할 수 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			if(repositoryT.countByCustId(idx[i])>0) {
				String custNm = repositoryCust.findById(idx[i]).orElse(null).getCustNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(custNm+"을(를) 참조하는 임시계약이 있어 삭제할 수 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			if(repositoryMAC.countByCustId(idx[i])>0) {
				String custNm = repositoryCust.findById(idx[i]).orElse(null).getCustNm();
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(custNm+"을(를) 참조하는 미팅참가자목록이 있어 삭제할 수 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
		}
		
		for(int i = 0; i < idx.length; i++) {
			repositoryCust.deleteById(idx[i]);
		}

		res.add(new ResponseInfo("삭제에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody CustDto cust, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Cust toUpdate = repositoryCust.findById(id).orElse(null);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}

		Org org = repositoryO.getOne(cust.getOrgId());
		
		toUpdate.setOrg(org);
		toUpdate.setCustNm(cust.getCustNm());
		toUpdate.setCustRankNm(cust.getCustRankNm());
		toUpdate.setEmail(cust.getEmail());
		toUpdate.setTelNo(cust.getTelNo());
		toUpdate.setCustTpCd(cust.getCustTpCd());

		repositoryCust.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
