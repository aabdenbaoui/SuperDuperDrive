package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.Blob;

@Controller
@RequestMapping("/files")
@ControllerAdvice
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("/uploadFile")
    public String uploadMultiPartFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes ra) throws IOException {
       if(fileService.createFile(file) != 0){
           ra.addFlashAttribute("fileSuccess", "");
       }else{
           ra.addFlashAttribute("fileErrorDuplicate", "");
       }
        return "redirect:/result";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam("id") Integer id, RedirectAttributes ra){
        ra.addFlashAttribute("deleteFileSuccess", "");
        fileService.deleteById(id);
        return "redirect:/result";
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFileById(@RequestParam("id") Integer id){
       File file = fileService.getFileById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
//        return "redirect:/home";
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileUploadError(RedirectAttributes ra){
        ra.addFlashAttribute("fileExceedSizeError", "You could not upload file bigger than 30 mb");
        return "redirect:/result";
    }
}
