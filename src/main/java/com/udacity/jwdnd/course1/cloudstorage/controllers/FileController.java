package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
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
       fileService.createFile(new File(StringUtils.cleanPath(file.getOriginalFilename()),  file.getContentType(), String.valueOf(file.getSize()),  file.getInputStream().readAllBytes()));
        return "redirect:/home";
    }

}
