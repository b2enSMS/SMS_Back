package com.b2en.sms.controller.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.file.FileListToClient;
import com.b2en.sms.dto.file.Headers;
import com.b2en.sms.dto.file.Response;
import com.b2en.sms.model.file.Scan;
import com.b2en.sms.repo.file.ScanRepository;
import com.b2en.sms.security.JwtTokenProvider;
import com.b2en.sms.service.file.ScanStorageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/scan")
@Slf4j
public class ScanController {
	
	@Autowired
    private ScanStorageService scanStorageService;
	
	@Autowired
    private ScanRepository repository;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
    
    @PostMapping("/upload")
    public Response uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
    
    	log.info("file:{}", file);
    	String fileName = file.getOriginalFilename();
    	
        Scan scan = new Scan();
        scan.setFileName(fileName);
        scan.setFileType(file.getContentType());
        scan = repository.save(scan);
        String scanId = scan.getId();
        
		scanStorageService.storeFile(file, scanId);
		
		String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/scan/download/").path(scanId)
				.toUriString();
		Headers headers = new Headers();
		headers.setAuthorization(tokenProvider.resolveToken(req));
		
		Response response = new Response();
		response.setName(fileName);
		response.setStatus("done");
		response.setUrl(url);
		response.setThumbUrl(url);
		response.setHeaders(headers);
		
        return response;
    }
    
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) {
    	
    	if(fileId.equals("")) {
			return ResponseEntity.noContent().build();
		}
    	
    	Scan scan = repository.findById(fileId).orElse(null);
    	if(scan==null) {
    		return ResponseEntity.noContent().build();
    	}
    	
    	String type[] = scan.getFileType().split("/");
    	String fileName = fileId+"."+type[1];
        // Load file as Resource
        Resource resource = scanStorageService.loadFileAsResource(fileName);
        if(resource==null) {
    		return ResponseEntity.noContent().build();
    	}

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + scan.getFileName() + "\"")
				.body(resource);
    }
    
	/*
	 * @GetMapping("/{scanId}") private ResponseEntity<Resource>
	 * getScanImg(@PathVariable String scanId) { Scan scan =
	 * repository.findById(scanId).orElseThrow(() -> new
	 * MyFileNotFoundException("File not found with id " + scanId)); String[]
	 * splitted = scan.getFileType().split("/"); // 확장자 가져오기 String fileName =
	 * scanId + "." + splitted[1]; // 파일명을 scanId로 변경
	 * 
	 * // Load file as Resource Resource resource =
	 * scanStorageService.loadFileAsResource(fileName);
	 * 
	 * // Try to determine file's content type String contentType =
	 * scan.getFileType();
	 * 
	 * return ResponseEntity.ok()
	 * .contentType(MediaType.parseMediaType(contentType))
	 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	 * resource.getFilename() + "\"") .body(resource); }
	 */
    
    @DeleteMapping(value = "")
	public ResponseEntity<List<ResponseInfo>> delete(@RequestBody FileListToClient[] dto) {
    	List<ResponseInfo> res = new ArrayList<ResponseInfo>();

		String[] idx = new String[dto.length];
		String[] scanIdArr = new String[dto.length];
		for(int i = 0; i < idx.length; i++) {
			idx[i] = dto[i].getUrl();
			if(idx[i]==null || idx[i].equals("")) {
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo("URL 정보가 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			String[] splitted1 = idx[i].split("/");
			String fn = splitted1[splitted1.length-1];
			String[] splitted2 = fn.split("\\.");
			String scanId = splitted2[0];
			if(!repository.existsById(scanId)) {
				res.add(new ResponseInfo("다움의 이유로 삭제에 실패했습니다: "));
				res.add(new ResponseInfo(scanId+"의 id를 가지는 row가 없습니다."));
				return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
			}
			scanIdArr[i] = scanId;
		}
		
		for(int i = 0; i < scanIdArr.length; i++) {
			String type = repository.getOne(scanIdArr[i]).getFileType();
			scanStorageService.deleteFile(scanIdArr[i], type);
				
			repository.deleteById(scanIdArr[i]);
		}

		res.add(new ResponseInfo("삭제에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
    
}
