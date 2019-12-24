package com.b2en.sms.controller.file;

import java.io.IOException;

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

import com.b2en.sms.dto.file.FileDto;
import com.b2en.sms.dto.file.ScanResponse;
import com.b2en.sms.entity.file.Scan;
import com.b2en.sms.repo.file.ScanRepository;
import com.b2en.sms.service.file.MyFileNotFoundException;
import com.b2en.sms.service.file.ScanStorageService;

@RestController
@RequestMapping("/api/scan")
public class ScanController {
	
	@Autowired
    private ScanStorageService scanStorageService;
	
	@Autowired
    private ScanRepository repository;
    
    @PostMapping("/upload")
    public ScanResponse uploadFile(@RequestParam("file") MultipartFile file) {
    
    	String fileName = file.getOriginalFilename();
    	
        Scan scan = new Scan();
        scan.setFileName(fileName);
        scan.setFileType(file.getContentType());
        scan = repository.save(scan);
        String scanId = scan.getId();
        
       scanStorageService.storeFile(file, scanId);
        
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/scan/download/")
                .path(scanId)
                .toUriString();

        return new ScanResponse(fileName, "sss", url, url);
    }
    
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) {
    	Scan scan = repository.findById(fileId).orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    	
        // Load file as Resource
        Resource resource = scanStorageService.loadFileAsResource(scan.getFileName());

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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @DeleteMapping(value = "")
	public ResponseEntity<Void> delete(@RequestBody FileDto dto) {
		String[] idx = new String[dto.getIdx().length];
		for(int i = 0; i < idx.length; i++) {
			idx[i] = dto.getIdx()[i].getResponse().getUrl();
			String[] splitted = idx[i].split("/");
			String scanId = splitted[splitted.length-1];
			
			String type = repository.findById(scanId).orElse(null).getFileType();
			scanStorageService.deleteFile(scanId, type);
			
			repository.deleteById(scanId);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
    
}
