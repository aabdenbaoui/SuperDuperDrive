package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;

@Controller
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("/uploadFile")
    public String uploadMultiPartFile(@RequestParam("fileUpload") MultipartFile file) throws IOException {
        System.out.println(file);
       fileService.createFile(file);
        return "redirect:/home";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") Integer id){
        fileService.deleteById(id);
        return "redirect:/home";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFileById(@RequestParam("id") Integer id){
       File file = fileService.getFileById(id);
        System.out.println(fileService.getFileById(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
//        return "redirect:/home";

    }

}
