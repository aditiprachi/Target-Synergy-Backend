package com.example.synergybackend.controller;

import com.example.synergybackend.model.LoadFile;
import com.example.synergybackend.model.Mcq;
import com.example.synergybackend.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.client.MongoCollection;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://targetsynergy.herokuapp.com/")
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileService.addFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        LoadFile loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }

}