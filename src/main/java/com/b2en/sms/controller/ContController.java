package com.b2en.sms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.b2en.sms.dto.ContAndLcnsDto;
import com.b2en.sms.dto.ContAndLcnsDtoForUpdate;
import com.b2en.sms.dto.DeleteDto;
import com.b2en.sms.dto.LcnsDto;
import com.b2en.sms.dto.LcnsDtoForUpdate;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.autocompleteinfo.ContAC;
import com.b2en.sms.dto.autocompleteinfo.ContACInterface;
import com.b2en.sms.dto.toclient.ContAndLcnsDtoToClient;
import com.b2en.sms.dto.toclient.ContChngHistDtoToClient;
import com.b2en.sms.dto.toclient.ContDtoToClient;
import com.b2en.sms.dto.toclient.LcnsDtoToClient;
import com.b2en.sms.entity.B2en;
import com.b2en.sms.entity.CmmnDetailCd;
import com.b2en.sms.entity.Cont;
import com.b2en.sms.entity.ContChngHist;
import com.b2en.sms.entity.ContDetail;
import com.b2en.sms.entity.ContDetailHist;
import com.b2en.sms.entity.Cust;
import com.b2en.sms.entity.Lcns;
import com.b2en.sms.entity.LcnsChngHist;
import com.b2en.sms.entity.Org;
import com.b2en.sms.entity.Prdt;
import com.b2en.sms.entity.file.Scan;
import com.b2en.sms.entity.pk.ContChngHistPK;
import com.b2en.sms.entity.pk.ContDetailHistPK;
import com.b2en.sms.entity.pk.ContDetailPK;
import com.b2en.sms.entity.pk.LcnsChngHistPK;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.CmmnDetailCdRepository;
import com.b2en.sms.repo.ContChngHistRepository;
import com.b2en.sms.repo.ContDetailHistRepository;
import com.b2en.sms.repo.ContDetailRepository;
import com.b2en.sms.repo.ContRepository;
import com.b2en.sms.repo.CustRepository;
import com.b2en.sms.repo.LcnsChngHistRepository;
import com.b2en.sms.repo.LcnsRepository;
import com.b2en.sms.repo.OrgRepository;
import com.b2en.sms.repo.PrdtRepository;
import com.b2en.sms.repo.file.ScanRepository;
import com.b2en.sms.service.file.FileList;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cont")
@Slf4j
public class ContController {
	
	@Autowired
	private ContRepository repositoryC;
	@Autowired
	private ContChngHistRepository repositoryCCH;
	@Autowired
	private ContDetailRepository repositoryCD;
	@Autowired
	private ContDetailHistRepository repositoryCDH;
	@Autowired
	private OrgRepository repositoryO;
	@Autowired
	private CustRepository repositoryCust;
	@Autowired
	private B2enRepository repositoryB;
	@Autowired
	private LcnsRepository repositoryL;
	@Autowired
	private LcnsChngHistRepository repositoryLCH;
	@Autowired
	private PrdtRepository repositoryP;
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	@Autowired
	private ScanRepository repositoryS;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/showincludedel", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDtoToClient>> getAll() {
		// delYn의 값에 상관없이 모두 불러옴
		List<Cont> entityList = repositoryC.findAll();
		List<ContDtoToClient> list;
		
		list = modelMapper.map(entityList, new TypeToken<List<ContDtoToClient>>() {
		}.getType());
		
		for(int i = 0; i < entityList.size(); i++) {
			int custId = (entityList.get(i).getCust()==null) ? 0 : entityList.get(i).getCust().getCustId();
			String custNm = (entityList.get(i).getCust()==null) ? "" : entityList.get(i).getCust().getCustNm();
			list.get(i).setCustId(custId);
			list.get(i).setCustNm(custNm);
			list.get(i).setOrgId(entityList.get(i).getOrg().getOrgId());
			list.get(i).setOrgNm(entityList.get(i).getOrg().getOrgNm());
			list.get(i).setEmpId(entityList.get(i).getB2en().getEmpId());
			list.get(i).setEmpNm(entityList.get(i).getB2en().getEmpNm());
		}

		return new ResponseEntity<List<ContDtoToClient>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDtoToClient>> getAllNotDeleted() {
		// delYn의 값이 "N"인 경우(삭제된걸로 처리되지 않은 경우)만 불러옴
		List<Cont> entityList = repositoryC.findByDelYnOrderByContIdDesc("N");
		
		List<ContDtoToClient> list;

		list = modelMapper.map(entityList, new TypeToken<List<ContDtoToClient>>() {
		}.getType());
		
		for(int i = 0; i < entityList.size(); i++) {
			int custId = (entityList.get(i).getCust()==null) ? 0 : entityList.get(i).getCust().getCustId();
			String custNm = (entityList.get(i).getCust()==null) ? "" : entityList.get(i).getCust().getCustNm();
			list.get(i).setCustId(custId);
			list.get(i).setCustNm(custNm);
			list.get(i).setOrgId(entityList.get(i).getOrg().getOrgId());
			list.get(i).setOrgNm(entityList.get(i).getOrg().getOrgNm());
			list.get(i).setEmpId(entityList.get(i).getB2en().getEmpId());
			list.get(i).setEmpNm(entityList.get(i).getB2en().getEmpNm());
			list.get(i).setTight(calculateIsTight(list.get(i).getMtncEndDt()));
		}
		
		return new ResponseEntity<List<ContDtoToClient>>(list, HttpStatus.OK);

	}
	
	private boolean calculateIsTight(String strEnd) {
		long alertRange = 90; // 남은 날짜가 이것 이하면 경고
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());
        
        try {
			Date endDate = sdf.parse(strEnd);
			Date todayDate = sdf.parse(strToday);
			
			long calDate = endDate.getTime() - todayDate.getTime();
			long calDateDay = calDate / (24*60*60*1000);
			
			return (calDateDay<=alertRange);
		} catch (ParseException e) {
			return false;
		}
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContAndLcnsDtoToClient> findContAndLcnsByContId(@PathVariable("id") int id) {
		
		Cont cont = repositoryC.getOne(id);
		
		ContAndLcnsDtoToClient contAndLcnsDtoToClient = modelMapper.map(cont, ContAndLcnsDtoToClient.class);
		int custId = (cont.getCust()==null) ? 0 : cont.getCust().getCustId();
		String custNm = (cont.getCust()==null) ? "" : cont.getCust().getCustNm();
		contAndLcnsDtoToClient.setCustId(custId);
		contAndLcnsDtoToClient.setCustNm(custNm);
		contAndLcnsDtoToClient.setOrgId(cont.getOrg().getOrgId());
		contAndLcnsDtoToClient.setOrgNm(cont.getOrg().getOrgNm());
		contAndLcnsDtoToClient.setEmpId(cont.getB2en().getEmpId());
		contAndLcnsDtoToClient.setEmpNm(cont.getB2en().getEmpNm());
		String headContNm = (cont.getHeadContId() == 0) ? "" : repositoryC.findByHeadContId(cont.getHeadContId()).getContNm();

		contAndLcnsDtoToClient.setHeadContNm(headContNm);
		String contTpNm = repositoryCDC.findByCmmnDetailCdPKCmmnDetailCd(cont.getContTpCd()).getCmmnDetailCdNm();
		contAndLcnsDtoToClient.setContTpNm(contTpNm);
		
		HashMap<String, String> cmmnDetailCdMap = new HashMap<String, String>();
		List<CmmnDetailCd> cmmnDetailCdList = repositoryCDC.findByCmmnDetailCdPKCmmnCd("lcns_tp_cd");
		for(int i = 0; i < cmmnDetailCdList.size(); i++) {
			cmmnDetailCdMap.put(cmmnDetailCdList.get(i).getCmmnDetailCdPK().getCmmnDetailCd(), cmmnDetailCdList.get(i).getCmmnDetailCdNm());
		}
		
		List<ContDetail> contDetail = repositoryCD.findByContDetailPKContId(id);
		LcnsDtoToClient[] lcnsDtoToClient = new LcnsDtoToClient[contDetail.size()];
		for(int i = 0; i < lcnsDtoToClient.length; i++) {
			lcnsDtoToClient[i] = modelMapper.map(contDetail.get(i).getLcns(), LcnsDtoToClient.class);
			lcnsDtoToClient[i].setContSeq(contDetail.get(i).getContDetailPK().getContSeq());
			lcnsDtoToClient[i].setPrdtId(contDetail.get(i).getLcns().getPrdt().getPrdtId());
			lcnsDtoToClient[i].setPrdtNm(contDetail.get(i).getLcns().getPrdt().getPrdtNm());
			lcnsDtoToClient[i].setContAmt(contDetail.get(i).getContAmt());
			lcnsDtoToClient[i].setLcnsTpNm(cmmnDetailCdMap.get(contDetail.get(i).getLcns().getLcnsTpCd()));
			lcnsDtoToClient[i].setFileList(getFileList(contDetail.get(i).getLcns().getScan()));
		}
		contAndLcnsDtoToClient.setLcns(lcnsDtoToClient);
		
		return new ResponseEntity<ContAndLcnsDtoToClient>(contAndLcnsDtoToClient, HttpStatus.OK);
	}
	
	private FileList[] getFileList(String id) {
		
		Scan scan = repositoryS.getOne(id);
		
		String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/scan/download/").path(id)
				.toUriString();
		
		FileList fileList = new FileList();
		fileList.setUid("-1");
		if(scan==null) {
			fileList.setName("");
			fileList.setUrl("");
			fileList.setThumbUrl("");
		} else {
			fileList.setName(scan.getFileName());
			fileList.setUrl(url);
			fileList.setThumbUrl(url);
		}
		fileList.setStatus("done");
		
		
		FileList[] result = {fileList};
		
		return result;
	}
	
	private String getScanIdFromUrl(String url) {
		
		if(url.equals("")||url==null) {
			return "";
		}
		String[] splitted1 = url.split("/");
		String fn = splitted1[splitted1.length-1];
		String[] splitted2 = fn.split("\\.");
		String scanId = splitted2[0];
		
		return scanId;
	}
	
	@GetMapping(value = "/aclist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContAC>> acList() {

		List<ContACInterface> entityList = repositoryC.findAllBy();
		List<ContAC> list = new ArrayList<ContAC>();
		
		for(int i = 0; i < entityList.size(); i++) {
			ContAC contAC = new ContAC();
			contAC.setHeadContId(entityList.get(i).getHeadContId());
			contAC.setHeadContNm(entityList.get(i).getContNm());
			list.add(contAC);
		}

		return new ResponseEntity<List<ContAC>>(list, HttpStatus.OK);
	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody ContAndLcnsDto contAndLcnsDto, BindingResult result) {
		
		log.debug("cont:{}", contAndLcnsDto);
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if (result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<ObjectError> errors = result.getAllErrors();
			for (int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		// ======================= Lcns 생성 ==========================
		LcnsDto[] lcnsDto = contAndLcnsDto.getLcns();
		int lcnsNum = lcnsDto.length;
		Lcns[] lcnsEntity = new Lcns[lcnsNum];
		
		HashMap<Integer, Prdt> prdtMap = new HashMap<Integer, Prdt>();
		List<Prdt> prdtList = repositoryP.findAll();
		for(int i = 0; i < prdtList.size(); i++) {
			prdtMap.put(prdtList.get(i).getPrdtId(), prdtList.get(i));
		}
		
		for(int i = 0; i < lcnsNum; i++) {
			lcnsEntity[i] = modelMapper.map(lcnsDto[i], Lcns.class);
			int prdtId = lcnsDto[i].getPrdtId();
			lcnsEntity[i].setPrdt(prdtMap.get(prdtId));
			String scanId = getScanIdFromUrl(lcnsDto[i].getFileList()[0].getUrl());
			lcnsEntity[i].setScan(scanId);
			
			lcnsEntity[i] = repositoryL.save(lcnsEntity[i]);
		}
		
		// ======================= Cont 생성 ==========================
		Cont contEntity = modelMapper.map(contAndLcnsDto, Cont.class);
		
		int custId = contAndLcnsDto.getCustId();
		Cust cust = repositoryCust.getOne(custId);
		int orgId = contAndLcnsDto.getOrgId();
		Org org = repositoryO.getOne(orgId);
		int empId = contAndLcnsDto.getEmpId();
		B2en b2en = repositoryB.getOne(empId);
		
		contEntity.setCust(cust);
		contEntity.setOrg(org);
		contEntity.setB2en(b2en);
		contEntity.setDelYn("N");
		
		// 유지보수 계약인 경우 모계약(구매 계약)의 Id, 구매 계약인 경우 0
		int headContId = (contAndLcnsDto.getContTpCd().equals("cont02")) ? contAndLcnsDto.getHeadContId() : 0;
		contEntity.setHeadContId(headContId);
		
		String[] contAmt = new String[lcnsDto.length];
		
		for(int i = 0; i < lcnsDto.length; i++) {
			contAmt[i] = lcnsDto[i].getContAmt();
		}
		
		int tot = 0;
		for (int i = 0; i < contAmt.length; i++) {
			tot += Integer.parseInt(contAmt[i]);
		}
		
		contEntity.setContTotAmt(Integer.toString(tot));
		
		contEntity = repositoryC.save(contEntity);
		
		// ======================= ContDetail 생성 ==========================
		int contId = contEntity.getContId();
		
		int maxSeq; // contSeq를 현존하는 가장 큰 contSeq값+1로 직접 할당하기 위한 변수
		if(repositoryCD.findMaxContSeq()==null) {
			maxSeq = 0;
		} else {
			maxSeq = repositoryCD.findMaxContSeq();
		}
		
		for (int i = 0; i < lcnsEntity.length; i++) {
			ContDetailPK contDetailPK = new ContDetailPK();
			contDetailPK.setContSeq(maxSeq+i+1); // contSeq 직접 할당
			contDetailPK.setContId(contId);
			ContDetail contDetail = new ContDetail();
			Lcns lcns = repositoryL.getOne(lcnsEntity[i].getLcnsId());

			contDetail.setContDetailPK(contDetailPK);
			contDetail.setCont(contEntity);
			contDetail.setLcns(lcns);
			contDetail.setContAmt(contAmt[i]);
			contDetail.setDelYn("N");
			contDetail.setContNote(lcnsDto[i].getContNote());

			repositoryCD.save(contDetail);
		}
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "")
	public ResponseEntity<Void> delete(@RequestBody DeleteDto id) {
		int[] idx = id.getIdx();
		for(int i = 0; i < idx.length; i++) {
			Cont cont = repositoryC.getOne(idx[i]);
			cont.setDelYn("Y");
			repositoryC.save(cont);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody ContAndLcnsDtoForUpdate contAndLcnsDto, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		Cont toUpdate = repositoryC.getOne(id);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		// ======================= ContChngHist 생성 =======================
		ContChngHist contChngHist = modelMapper.map(toUpdate, ContChngHist.class);
		ContChngHistPK contChngHistPK = new ContChngHistPK();
		contChngHistPK.setContId(id);
		contChngHist.setContChngHistPK(contChngHistPK);
		contChngHist.setCont(toUpdate);

		repositoryCCH.save(contChngHist);
		
		// ======================= Cont 수정 ==========================
		int custId = contAndLcnsDto.getCustId();
		Cust cust = repositoryCust.getOne(custId);
		int orgId = contAndLcnsDto.getOrgId();
		Org org = repositoryO.getOne(orgId);
		int empId = contAndLcnsDto.getEmpId();
		B2en b2en = repositoryB.getOne(empId);
		LcnsDtoForUpdate[] lcnsDto = contAndLcnsDto.getLcns();

		toUpdate.setCust(cust);
		toUpdate.setOrg(org);
		toUpdate.setB2en(b2en);
		toUpdate.setHeadContId(contAndLcnsDto.getHeadContId());
		toUpdate.setContNm(contAndLcnsDto.getContNm());
		toUpdate.setContDt(java.sql.Date.valueOf(contAndLcnsDto.getContDt()));
		toUpdate.setContReportNo(contAndLcnsDto.getContReportNo());
		toUpdate.setContTpCd(contAndLcnsDto.getContTpCd());
		toUpdate.setInstallDt(java.sql.Date.valueOf(contAndLcnsDto.getInstallDt()));
		toUpdate.setCheckDt(java.sql.Date.valueOf(contAndLcnsDto.getCheckDt()));
		toUpdate.setMtncStartDt(java.sql.Date.valueOf(contAndLcnsDto.getMtncStartDt()));
		toUpdate.setMtncEndDt(java.sql.Date.valueOf(contAndLcnsDto.getMtncEndDt()));

		String[] contAmt = new String[lcnsDto.length];

		for (int i = 0; i < lcnsDto.length; i++) {
			contAmt[i] = lcnsDto[i].getContAmt();
		}

		int tot = 0;
		for (int i = 0; i < contAmt.length; i++) {
			tot += Integer.parseInt(contAmt[i]);
		}

		toUpdate.setContTotAmt(Integer.toString(tot));

		repositoryC.save(toUpdate);
		
		// ======================= contDetail, Lcns 탐색(생성/수정/삭제) ==========================
		List<ContDetail> cdList = repositoryCD.findByContDetailPKContId(id); // 기존의 contDetail
		
		HashMap<Integer, Prdt> prdtMap = new HashMap<Integer, Prdt>();
		List<Prdt> prdtList = repositoryP.findAll();
		for(int i = 0; i < prdtList.size(); i++) {
			prdtMap.put(prdtList.get(i).getPrdtId(), prdtList.get(i));
		}
		
		for(int i = 0; i < lcnsDto.length; i++) {
			ContDetail contDetail = repositoryCD.findByContDetailPKContSeq(lcnsDto[i].getContSeq()); // 해당 contSeq를 가진 contDetail이 있나 탐색
			if(contDetail == null) { // 새로 생김
				Lcns lcns = modelMapper.map(lcnsDto[i], Lcns.class);
				int prdtId = lcnsDto[i].getPrdtId();
				lcns.setPrdt(prdtMap.get(prdtId));
				String scanId = getScanIdFromUrl(lcnsDto[i].getFileList()[0].getUrl());
				lcns.setScan(scanId);
				lcns = repositoryL.save(lcns);
				
				contDetail = new ContDetail();
				ContDetailPK contDetailPK = new ContDetailPK();
				contDetailPK.setContId(id);
				
				Integer findMaxSeq = repositoryCD.findMaxContSeq();
				int maxSeq = (findMaxSeq==null) ? 0 : findMaxSeq;// contSeq를 현존하는 가장 큰 contSeq값+1로 직접 할당하기 위한 변수
				contDetailPK.setContSeq(maxSeq+1);
				contDetail.setContDetailPK(contDetailPK);
				contDetail.setCont(toUpdate);
				contDetail.setLcns(lcns);
				contDetail.setContAmt(lcnsDto[i].getContAmt());
				contDetail.setDelYn("N");
				contDetail.setContNote(lcnsDto[i].getContNote());
				
				repositoryCD.save(contDetail);
			} else { // 기존에 있던거 수정
				// 1. contDetailHist 생성
				ContDetailHist contDetailHist = new ContDetailHist();
				ContDetailHistPK contDetailHistPK = new ContDetailHistPK();
				Integer findMaxDetailSeq = repositoryCDH.findMaxDetailSeq();
				int maxDetailSeq = (findMaxDetailSeq==null) ? 0 : findMaxDetailSeq;// detailSeq를 현존하는 가장 큰 detailSeq값+1로 직접 할당하기 위한 변수
				contDetailHistPK.setDetailSeq(maxDetailSeq+1);
				contDetailHistPK.setContDetailPK(contDetail.getContDetailPK());
				contDetailHist.setContDetailHistPK(contDetailHistPK);
				contDetailHist.setContDetail(contDetail);
				contDetailHist.setContAmt(contDetail.getContAmt());
				contDetailHist.setLcns(contDetail.getLcns());
				repositoryCDH.save(contDetailHist);
				
				cdList.remove(contDetail);
				
				// 2. LcnsChngHist 생성
				Lcns lcns = repositoryCD.findByContDetailPKContSeq(lcnsDto[i].getContSeq()).getLcns();
				LcnsChngHist lcnsChngHist = modelMapper.map(lcns, LcnsChngHist.class);
				LcnsChngHistPK lcnsChngHistPK = new LcnsChngHistPK();
				Integer findMaxHistSeq = repositoryCD.findMaxContSeq();
				int maxHistSeq = (findMaxHistSeq==null) ? 0 : findMaxHistSeq;// histSeq를 현존하는 가장 큰 histSeq값+1로 직접 할당하기 위한 변수
				lcnsChngHistPK.setHistSeq(maxHistSeq+1);
				lcnsChngHistPK.setLcnsId(lcns.getLcnsId());
				lcnsChngHist.setLcnsChngHistPK(lcnsChngHistPK);
				lcnsChngHist.setLcns(lcns);
				repositoryLCH.save(lcnsChngHist);
				
				// 3. Lcns 수정
				int prdtId = lcnsDto[i].getPrdtId();
				Prdt prdt = repositoryP.getOne(prdtId);
				lcns.setPrdt(prdt);
				lcns.setLcnsNo(lcnsDto[i].getLcnsNo());
				lcns.setLcnsIssuDt(java.sql.Date.valueOf(lcnsDto[i].getLcnsIssuDt()));
				lcns.setLcnsTpCd(lcnsDto[i].getLcnsTpCd());
				lcns.setCertNo(lcnsDto[i].getCertNo());
				lcns.setLcnsStartDt(java.sql.Date.valueOf(lcnsDto[i].getLcnsStartDt()));
				lcns.setLcnsEndDt(java.sql.Date.valueOf(lcnsDto[i].getLcnsEndDt()));
				String scanId = getScanIdFromUrl(lcnsDto[i].getFileList()[0].getUrl());
				lcns.setScan(scanId);
				lcns = repositoryL.save(lcns);
				
				// 4. ContDetail 수정
				contDetail.setCont(toUpdate);
				contDetail.setLcns(lcns);
				contDetail.setContAmt(lcnsDto[i].getContAmt());
				contDetail.setContNote(lcnsDto[i].getContNote());
				repositoryCD.save(contDetail);
			}
		}
		
		for (int i = 0; i < cdList.size(); i++) { // 있다가 없어짐
			repositoryCD.deleteByContDetailPKContSeq(cdList.get(i).getContDetailPK().getContSeq());
			repositoryL.deleteById(cdList.get(i).getLcns().getLcnsId());
		}
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
	
	@GetMapping(value="/hist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContChngHistDtoToClient>> findHistByContId(@PathVariable("id") int id) {
		
		List<ContChngHist> contChngHistList = repositoryCCH.findByContChngHistPKContId(id);
		List<ContChngHistDtoToClient> contChngHistDtoToClientList = new ArrayList<ContChngHistDtoToClient>();
		String contNm = repositoryC.getOne(id).getContNm();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i < contChngHistList.size(); i++) {
			ContChngHistDtoToClient contChngHistDtoToClient = new ContChngHistDtoToClient();
			ContChngHist contChngHist = contChngHistList.get(i);
			contChngHistDtoToClient.setHistSeq(contChngHist.getContChngHistPK().getHistSeq());
			contChngHistDtoToClient.setCustNm(contChngHist.getCust().getCustNm());
			contChngHistDtoToClient.setOrgNm(contChngHist.getOrg().getOrgNm());
			contChngHistDtoToClient.setEmpNm(contChngHist.getB2en().getEmpNm());
			contChngHistDtoToClient.setContDt(sdf.format(contChngHist.getContDt()));
			contChngHistDtoToClient.setContTotAmt(contChngHist.getContTotAmt());
			contChngHistDtoToClient.setInstallDt(sdf.format(contChngHist.getInstallDt()));
			contChngHistDtoToClient.setCheckDt(sdf.format(contChngHist.getCheckDt()));
			contChngHistDtoToClient.setMtncStartDt(sdf.format(contChngHist.getMtncStartDt()));
			contChngHistDtoToClient.setMtncEndDt(sdf.format(contChngHist.getMtncEndDt()));
			contChngHistDtoToClient.setCreatedDate(contChngHist.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			contChngHistDtoToClient.setContNm(contNm);
			
			contChngHistDtoToClientList.add(contChngHistDtoToClient);
		}
		
		return new ResponseEntity<List<ContChngHistDtoToClient>>(contChngHistDtoToClientList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/detail/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContDetail>> getAllDetail() {

		List<ContDetail> entityList = repositoryCD.findAll();

		return new ResponseEntity<List<ContDetail>>(entityList, HttpStatus.OK);

	}
	
	@DeleteMapping(value = "/detail/{id}")
	public ResponseEntity<Void> deleteDetail(@PathVariable("id") int id) {
		// ContDetail은 delete시 실제로 DB에서 삭제하지 않고 delYn이 "N"에서 "Y"로 변경되게 함
		ContDetail contDetail = repositoryCD.findByContDetailPKContSeq(id);
		contDetail.setDelYn("Y");
		repositoryCD.save(contDetail);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
}
