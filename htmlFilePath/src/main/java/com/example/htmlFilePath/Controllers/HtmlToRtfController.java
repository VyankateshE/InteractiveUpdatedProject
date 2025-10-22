package com.example.htmlFilePath.Controllers;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.htmlFilePath.Services.HtmlToDocRtfService;
import com.example.htmlFilePath.Services.WordConversionService;

@RestController
@RequestMapping("/api")
public class HtmlToRtfController {

    @Autowired
    private HtmlToDocRtfService htmlToRtfService;
    
    @Autowired
    private WordConversionService wordConversionService;

    @PostMapping("/wordToRtf")
    public ResponseEntity<byte[]> convertUsingWord(@RequestPart("file") MultipartFile file) throws Exception {
        File tempDoc = File.createTempFile("input-", ".doc");
        file.transferTo(tempDoc);

        byte[] rtfBytes = wordConversionService.convertDocToRtf(tempDoc);
        tempDoc.delete();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + UUID.randomUUID() + ".rtf")
                .contentType(MediaType.parseMediaType("application/rtf"))
                .body(rtfBytes);
    }


    @PostMapping("/toDoc")
    public ResponseEntity<byte[]> convertToDoc(@RequestPart("file") MultipartFile file) throws Exception {
        byte[] docBytes = htmlToRtfService.convertHtmlToDoc(file.getInputStream());
        
		String randomFileName = UUID.randomUUID().toString() + ".doc";


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Doc"+randomFileName)
                .contentType(MediaType.parseMediaType("application/msword"))
                .body(docBytes);
    }

    @PostMapping("/toRtf")
    public ResponseEntity<byte[]> convertToRtfs(@RequestPart("file") MultipartFile file) throws Exception {
        byte[] rtfBytes = htmlToRtfService.convertHtmlToRtf(file.getInputStream());
        
		String randomFileName = UUID.randomUUID().toString() + ".rtf";


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Rtf"+randomFileName)
                .contentType(MediaType.parseMediaType("application/rtf"))
                .body(rtfBytes);
    }
    
    
    @PostMapping("/docToRtf")
    public ResponseEntity<byte[]> convertDocToRtf(@RequestPart("file") MultipartFile file) throws Exception {
        byte[] rtfBytes = htmlToRtfService.convertDocToRtf(file.getInputStream());
        
        String randomFileName = UUID.randomUUID().toString() + ".rtf";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Rtf" + randomFileName)
                .contentType(MediaType.parseMediaType("application/rtf"))
                .body(rtfBytes);
    }
    
}