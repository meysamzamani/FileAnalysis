package com.meysamzamani.file_analysis.controllers;

import com.meysamzamani.file_analysis.models.FileName;
import com.meysamzamani.file_analysis.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path ="api/v1/file")
public class FileController {

    private final IStorageService fileSystemStorageService;

    @Autowired
    public FileController(IStorageService fileSystemStorageService) {
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @GetMapping("/")
    public ResponseEntity<List<FileName>> listUploadedFiles() {
        List<FileName> files = fileSystemStorageService.loadAll().stream().map(file -> FileName.valueOf(file.getFileName().toString())).toList();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        fileSystemStorageService.store(file);
        return new ResponseEntity<>("You successfully uploaded " + file.getOriginalFilename(), HttpStatus.CREATED);
    }

    @GetMapping(path ="{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String fileName) {
        Resource file = fileSystemStorageService.loadAsResource(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
